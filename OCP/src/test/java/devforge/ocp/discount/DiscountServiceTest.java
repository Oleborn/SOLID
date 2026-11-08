package devforge.ocp.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.discount.rule.FirstOrderRule;
import devforge.ocp.discount.rule.HolidayRule;
import devforge.ocp.discount.rule.PromoCodeRule;
import devforge.ocp.discount.rule.SecondItemRule;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscountServiceTest {

    private final DiscountService serviceWithoutHoliday = new DiscountService(List.of(
            new FirstOrderRule(),
            new SecondItemRule(),
            new PromoCodeRule()
    ));

    private final DiscountService serviceWithHoliday = new DiscountService(List.of(
            new FirstOrderRule(),
            new SecondItemRule(),
            new PromoCodeRule(),
            new HolidayRule(Clock.fixed(Instant.parse("2026-12-25T12:00:00Z"), ZoneOffset.UTC))
    ));

    @Test
    void returnsTotalWhenNoRulesApplicable() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, serviceWithoutHoliday.calculate(order)
                .compareTo(new BigDecimal("1000.00")));
    }

    @Test
    void appliesFirstSecondAndPromoRulesInPriorityOrder() {
        Order order = new Order();
        order.setFirstOrder(true);
        order.setPromoCode("SUMMER");
        order.addItem(item("A", "1000.00", 2));

        // base: 1000.00 × 2 = 2000.00
        // first (10): 2000.00 × 0.9  = 1800.00
        // second (20): 1800.00 × 0.95 = 1710.00
        // promo (30): 1710.00 − 200  = 1510.00
        assertEquals(0, serviceWithoutHoliday.calculate(order)
                .compareTo(new BigDecimal("1510.00")));
    }

    @Test
    void appliesHolidayRuleLast() {
        Order order = new Order();
        order.setFirstOrder(true);
        order.setPromoCode("SUMMER");
        order.addItem(item("A", "1000.00", 2));

        // base: 1000.00 × 2 = 2000.00
        // first (10): 2000.00 × 0.9  = 1800.00
        // second (20): 1800.00 × 0.95 = 1710.00
        // promo (30): 1710.00 − 200  = 1510.00
        // holiday (40): 1510.00 × 0.85 = 1283.50
        assertEquals(0, serviceWithHoliday.calculate(order)
                .compareTo(new BigDecimal("1283.50")));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}