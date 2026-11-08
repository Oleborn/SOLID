package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CappedPercentageDiscount implements Discount {

    private static final BigDecimal CAP = new BigDecimal("1000");

    private final PercentageDiscount base = new PercentageDiscount();

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
        BigDecimal discounted = base.apply(order, total);
        BigDecimal saving = total.subtract(discounted);
        if (saving.compareTo(CAP) > 0) {
            return total.subtract(CAP);
        }
        return discounted;
    }
}