package devforge.ocp.discount.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PromoCodeRuleTest {

    private final PromoCodeRule rule = new PromoCodeRule();

    @Test
    void appliesWhenPromoCodePresent() {
        Order order = new Order();
        order.setPromoCode("SUMMER");

        assertTrue(rule.isApplicable(order));
        assertEquals(0, rule.apply(order, new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("800.00")));
    }

    @Test
    void skipsWhenPromoCodeIsNull() {
        Order order = new Order();
        assertFalse(rule.isApplicable(order));
    }

    @Test
    void skipsWhenPromoCodeIsBlank() {
        Order order = new Order();
        order.setPromoCode("   ");
        assertFalse(rule.isApplicable(order));
    }
}