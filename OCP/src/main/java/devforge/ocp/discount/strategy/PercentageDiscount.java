package devforge.ocp.discount.strategy;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PercentageDiscount implements DiscountStrategy {

    @Override
    public String type() {
        return "PERCENTAGE";
    }

    @Override
    public String description() {
        return "10% off everything";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        return total.multiply(new BigDecimal("0.9"));
    }
}