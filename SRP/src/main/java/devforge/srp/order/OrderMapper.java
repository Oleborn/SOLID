package devforge.srp.order;

import devforge.srp.order.dto.OrderItemRequest;
import devforge.srp.order.dto.OrderItemResponse;
import devforge.srp.order.dto.OrderRequest;
import devforge.srp.order.dto.OrderResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Преобразует заказы между слоями: запрос → сущность, сущность → ответ API.
 *
 * <p>Нюансы использования: сумма для ответа считается через {@link InvoiceService}.
 * Маппер не хранит состояние и не работает с базой — только переводит одни
 * объекты в другие.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final InvoiceService invoiceService;

    /**
     * Преобразует позиции из запроса в сущности.
     *
     * <p>Нюансы использования: связь с заказом проставляется позже, в
     * {@link Order#addItem(OrderItem)}.</p>
     *
     * @param request запрос на создание заказа
     * @return позиции для сохранения
     * @author oleborn
     * @since 29.10.2026
     */
    public List<OrderItem> toItems(OrderRequest request) {
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.items()) {
            OrderItem item = new OrderItem();

            item.setProductId(itemRequest.productId());
            item.setPrice(itemRequest.price());
            item.setQuantity(itemRequest.quantity());
            item.setPurchasePrice(itemRequest.purchasePrice());

            items.add(item);
        }

        return items;
    }

    /**
     * Собирает ответ API из сохранённого заказа.
     *
     * <p>Нюансы использования: закупочная цена в ответ не попадает, даже если
     * она есть в сущности. Сумма считается через {@link InvoiceService}.</p>
     *
     * @param order заказ, который отдаётся клиенту
     * @return заказ в виде DTO
     * @author oleborn
     * @since 29.10.2026
     */
    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            items.add(new OrderItemResponse(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getPrice()
            ));
        }

        return new OrderResponse(
                order.getId(),
                items,
                invoiceService.generateInvoice(order),
                order.getCreatedAt()
        );
    }
}