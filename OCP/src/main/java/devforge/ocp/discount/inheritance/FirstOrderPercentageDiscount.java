package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class FirstOrderPercentageDiscount extends PercentageDiscount {

    @Override
    public String type() {
        return "FIRST_ORDER";
    }

    @Override
    public String description() {
        return "10% off first order";
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        if (order.isFirstOrder()) {
            return super.apply(order, total);
        }
        return total;
    }
}