package devforge.ocp.discount.rule;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PromoCodeRule implements DiscountRule {

    @Override
    public boolean isApplicable(Order order) {
        return order.getPromoCode() != null && !order.getPromoCode().isBlank();
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        return total.subtract(new BigDecimal("200"));
    }

    @Override
    public int priority() {
        return 30;
    }
}