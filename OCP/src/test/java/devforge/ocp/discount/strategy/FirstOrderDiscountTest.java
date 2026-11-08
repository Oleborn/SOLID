package devforge.ocp.discount.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FirstOrderDiscountTest {

    private final FirstOrderDiscount discount = new FirstOrderDiscount();

    @Test
    void appliesForFirstOrder() {
        Order order = new Order();
        order.setFirstOrder(true);
        assertEquals(0, discount.apply(order, new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("900.00")));
    }

    @Test
    void skipsForRegularOrder() {
        Order order = new Order();
        order.setFirstOrder(false);
        assertEquals(0, discount.apply(order, new BigDecimal("1000.00"))
                .compareTo(new BigDecimal("1000.00")));
    }
}