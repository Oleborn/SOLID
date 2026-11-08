package devforge.ocp.discount.inheritance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CappedPercentageDiscountTest {

    private final CappedPercentageDiscount discount = new CappedPercentageDiscount();

    @Test
    void capsDiscountAt1000() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("20000.00"))
                .compareTo(new BigDecimal("19000.00")));
    }

    @Test
    void appliesFullDiscountBelowCap() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("5000.00"))
                .compareTo(new BigDecimal("4500.00")));
    }
}