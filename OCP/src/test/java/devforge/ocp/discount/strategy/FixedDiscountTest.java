package devforge.ocp.discount.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FixedDiscountTest {

    private final FixedDiscount discount = new FixedDiscount();

    @Test
    void appliesWhenThresholdReached() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("6000.00"))
                .compareTo(new BigDecimal("5500.00")));
    }

    @Test
    void skipsBelowThreshold() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("1000.00")));
    }
}