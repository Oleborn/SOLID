package devforge.ocp.discount;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {

    public BigDecimal calculate(Order order, String type) {
        BigDecimal total = sumItems(order);

        switch (type) {
            case "PERCENTAGE" -> total = total.multiply(new BigDecimal("0.9"));
            case "FIXED" -> {
                if (total.compareTo(new BigDecimal("5000")) >= 0) {
                    total = total.subtract(new BigDecimal("500"));
                }
            }
            case "THRESHOLD" -> {
                if (total.compareTo(new BigDecimal("1000")) > 0) {
                    total = total.multiply(new BigDecimal("0.95"));
                }
            }
            case "FIRST_ORDER" -> {
                if (order.isFirstOrder()) {
                    total = total.multiply(new BigDecimal("0.9"));
                }
            }
            case "NONE" -> {}
            default -> throw new IllegalArgumentException("Unknown type: " + type);
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