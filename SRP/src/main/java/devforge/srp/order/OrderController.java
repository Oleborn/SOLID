package devforge.srp.order;

import devforge.srp.order.dto.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * HTTP-точки входа для работы с заказами.
 *
 * <p>Нюансы использования: наружу отдаются только DTO, сущность {@link Order}
 * в ответы не попадает. Зависимости передаются через конструктор, который
 * генерирует Lombok.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final TaxReportService taxReportService;

    /**
     * Создаёт заказ по запросу от администратора.
     *
     * <p>Нюансы использования: возвращает 201 Created и заказ вместе с посчитанной
     * суммой. Позиции сохраняются каскадом вместе с заказом.</p>
     *
     * @param request позиции создаваемого заказа
     * @return созданный заказ
     * @author oleborn
     * @since 29.10.2026
     */
    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestBody OrderRequest request
    ) {
        Order order = orderService.createOrder(toItems(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(order));
    }

    /**
     * Возвращает заказ по идентификатору.
     *
     * <p>Нюансы использования: временно возвращает сущность напрямую, чтобы мобильное
     * приложение получило список заказов как можно быстрее. Закупочная цена при этом
     * попадает в ответ.</p>
     *
     * @param id идентификатор заказа
     * @return заказ вместе с позициями
     * @author oleborn
     * @since 29.10.2026
     */
    @GetMapping("/{id}")
    public Order get(
            @PathVariable Long id
    ) {
        return orderService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    /**
     * Возвращает налоговый отчёт по заказу.
     *
     * <p>Нюансы использования: если заказа нет, отвечает 404 Not Found. Сумма
     * считается через {@link TaxReportService}, контроллер её не дублирует.</p>
     *
     * @param id идентификатор заказа
     * @return отчёт с посчитанной суммой
     * @author oleborn
     * @since 29.10.2026
     */
    @GetMapping("/{id}/tax-report")
    public TaxReportResponse taxReport(
            @PathVariable Long id
    ) {
        Order order = orderService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        return new TaxReportResponse(order.getId(), taxReportService.generateReport(order));
    }

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
    private List<OrderItem> toItems(OrderRequest request) {
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
     * <p>Нюансы использования: сумма считается через {@link InvoiceService}, поэтому
     * контроллер не дублирует формулу расчёта. Закупочная цена в ответ не попадает.</p>
     *
     * @param order заказ, который отдаётся клиенту
     * @return заказ в виде DTO
     * @author oleborn
     * @since 29.10.2026
     */
    private OrderResponse toResponse(Order order) {
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
