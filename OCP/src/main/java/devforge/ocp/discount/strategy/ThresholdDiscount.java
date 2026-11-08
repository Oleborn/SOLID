package devforge.ocp.discount.strategy;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ThresholdDiscount implements DiscountStrategy {

    @Override
    public String type() {
        return "THRESHOLD";
    }

    @Override
    public String description() {
        return "5% off orders over 1000";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        if (total.compareTo(new BigDecimal("1000")) > 0) {
            return total.multiply(new BigDecimal("0.95"));
        }
        return total;
    }
}