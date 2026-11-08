package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PercentageDiscount extends Discount {

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
        if (total.compareTo(new BigDecimal("1000")) < 0) {
            return total;
        }
        return total.multiply(new BigDecimal("0.9"));
    }
}