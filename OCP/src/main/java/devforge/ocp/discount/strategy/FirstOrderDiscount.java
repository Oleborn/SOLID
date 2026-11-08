package devforge.ocp.discount.strategy;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FirstOrderDiscount implements DiscountStrategy {

    @Override
    public String type() {
        return "FIRST_ORDER";
    }

    @Override
    public String description() {
        return "10% off first order";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        if (order.isFirstOrder()) {
            return total.multiply(new BigDecimal("0.9"));
        }
        return total;
    }
}