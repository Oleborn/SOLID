package devforge.ocp.discount;

import devforge.ocp.discount.dto.DiscountItemRequest;
import devforge.ocp.discount.dto.DiscountRequest;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class DiscountRequestMapper {

    public Order toOrder(DiscountRequest request) {
        Order order = new Order();
        order.setFirstOrder(request.firstOrder());
        order.setPromoCode(request.promoCode());

        for (DiscountItemRequest itemRequest : request.items()) {
            OrderItem item = new OrderItem();
            item.setProductId(itemRequest.productId());
            item.setPrice(itemRequest.price());
            item.setQuantity(itemRequest.quantity());
            order.addItem(item);
        }

        return order;
    }
}