package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FixedDiscount extends Discount {

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