package devforge.ocp.discount.rule;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class SecondItemRule implements DiscountRule {

    @Override
    public boolean isApplicable(Order order) {
        return order.getItems().stream().anyMatch(item -> item.getQuantity() >= 2);
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        return total.multiply(new BigDecimal("0.95"));
    }

    @Override
    public int priority() {
        return 20;
    }
}