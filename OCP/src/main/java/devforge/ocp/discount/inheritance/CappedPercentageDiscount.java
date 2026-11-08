package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CappedPercentageDiscount extends PercentageDiscount {

    private static final BigDecimal CAP = new BigDecimal("1000");

    @Override
    public String type() {
        return "CAPPED";
    }

    @Override
    public String description() {
        return "10% off, capped at 1000";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        BigDecimal discounted = super.apply(order, total);
        BigDecimal saving = total.subtract(discounted);
        if (saving.compareTo(CAP) > 0) {
            return total.subtract(CAP);
        }
        return discounted;
    }
}