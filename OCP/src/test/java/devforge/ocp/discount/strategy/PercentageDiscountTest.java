package devforge.ocp.discount.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PercentageDiscountTest {

    private final PercentageDiscount discount = new PercentageDiscount();

    @Test
    void applies10Percent() {
        assertEquals(0, discount.apply(new Order(), new BigDecimal("200.00"))
                .compareTo(new BigDecimal("180.00")));
    }
}