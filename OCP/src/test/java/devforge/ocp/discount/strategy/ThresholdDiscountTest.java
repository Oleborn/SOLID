package devforge.ocp.discount.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ThresholdDiscountTest {

    private final ThresholdDiscount discount = new ThresholdDiscount();

    @Test
    void appliesAboveThreshold() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("2000.00"))
                .compareTo(new BigDecimal("1900.00")));
    }

    @Test
    void skipsAtThreshold() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("1000.00")));
    }
}