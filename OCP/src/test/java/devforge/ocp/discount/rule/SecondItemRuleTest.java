package devforge.ocp.discount.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SecondItemRuleTest {

    private final SecondItemRule rule = new SecondItemRule();

    @Test
    void appliesWhenItemQuantityAtLeastTwo() {
        Order order = new Order();
        OrderItem item = new OrderItem();
        item.setProductId("A");
        item.setPrice(new BigDecimal("1000.00"));
        item.setQuantity(2);
        order.addItem(item);

        assertTrue(rule.isApplicable(order));
        assertEquals(0, rule.apply(order, new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("950.00")));
    }

    @Test
    void skipsWhenAllItemsSingleQuantity() {
        Order order = new Order();
        OrderItem item = new OrderItem();
        item.setProductId("A");
        item.setPrice(new BigDecimal("1000.00"));
        item.setQuantity(1);
        order.addItem(item);

        assertFalse(rule.isApplicable(order));
    }
}