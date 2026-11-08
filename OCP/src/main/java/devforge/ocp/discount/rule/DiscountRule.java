package devforge.ocp.discount.rule;

import devforge.ocp.order.Order;
import java.math.BigDecimal;

public interface DiscountRule {
    boolean isApplicable(Order order);
    BigDecimal apply(Order order, BigDecimal total);
    int priority();
}