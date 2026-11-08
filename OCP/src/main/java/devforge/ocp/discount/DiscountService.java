package devforge.ocp.discount;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public BigDecimal calculate(Order order, String type) {
        BigDecimal total = sumItems(order);

        if ("PERCENTAGE".equals(type)) {
            total = total.multiply(new BigDecimal("0.9"));
        }

        return total;
    }

    private BigDecimal sumItems(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}