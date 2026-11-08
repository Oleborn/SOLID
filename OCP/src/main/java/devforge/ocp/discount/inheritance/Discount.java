package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;

public abstract class Discount {

    public abstract String type();

    public abstract String description();

    public abstract BigDecimal apply(Order order, BigDecimal total);
}