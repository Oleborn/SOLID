package devforge.ocp.discount.rule;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidayRule implements DiscountRule {

    private final Clock clock;

    @Override
    public boolean isApplicable(Order order) {
        LocalDate today = LocalDate.now(clock);
        return today.getMonth() == Month.DECEMBER && today.getDayOfMonth() >= 25;
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        return total.multiply(new BigDecimal("0.85"));
    }

    @Override
    public int priority() {
        return 40;
    }
}