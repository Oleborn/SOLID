package devforge.ocp.discount.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class HolidayRuleTest {

    @Test
    void appliesOnDecember25() {
        Clock clock = Clock.fixed(Instant.parse("2026-12-25T12:00:00Z"), ZoneOffset.UTC);
        HolidayRule rule = new HolidayRule(clock);

        assertTrue(rule.isApplicable(new Order()));
        assertEquals(0, rule.apply(new Order(), new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("850.00")));
    }

    @Test
    void skipsOnRegularDay() {
        Clock clock = Clock.fixed(Instant.parse("2026-06-15T12:00:00Z"), ZoneOffset.UTC);
        HolidayRule rule = new HolidayRule(clock);

        assertFalse(rule.isApplicable(new Order()));
    }
}