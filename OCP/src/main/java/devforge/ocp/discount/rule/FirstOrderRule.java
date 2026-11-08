package devforge.ocp.discount.rule;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FirstOrderRule implements DiscountRule {

    @Override
    public boolean isApplicable(Order order) {
        return order.isFirstOrder();
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        return total.multiply(new BigDecimal("0.9"));
    }

    @Override
    public int priority() {
        return 10;
    }
}