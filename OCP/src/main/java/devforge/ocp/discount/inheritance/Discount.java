package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;

public interface Discount {
    String type();
    String description();
    BigDecimal apply(Order order, BigDecimal total);
}