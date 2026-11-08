package devforge.ocp.discount.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FirstOrderRuleTest {

    private final FirstOrderRule rule = new FirstOrderRule();

    @Test
    void appliesForFirstOrder() {
        Order order = new Order();
        order.setFirstOrder(true);
        assertTrue(rule.isApplicable(order));
        assertEquals(0, rule.apply(order, new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("900.00")));
    }

    @Test
    void skipsForRegularOrder() {
        Order order = new Order();
        order.setFirstOrder(false);
        assertFalse(rule.isApplicable(order));
    }
}