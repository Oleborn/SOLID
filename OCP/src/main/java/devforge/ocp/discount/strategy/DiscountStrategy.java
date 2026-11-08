package devforge.ocp.discount.strategy;

import devforge.ocp.order.Order;
import java.math.BigDecimal;

public interface DiscountStrategy {
    String type();
    String description();
    BigDecimal apply(Order order, BigDecimal total);
}