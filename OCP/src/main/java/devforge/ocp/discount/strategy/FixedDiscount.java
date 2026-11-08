package devforge.ocp.discount.strategy;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FixedDiscount implements DiscountStrategy {

    @Override
    public String type() {
        return "FIXED";
    }

    @Override
    public String description() {
        return "500 off orders over 5000";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        if (total.compareTo(new BigDecimal("5000")) >= 0) {
            return total.subtract(new BigDecimal("500"));
        }
        return total;
    }
}