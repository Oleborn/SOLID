package devforge.ocp.discount.inheritance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FirstOrderPercentageDiscountTest {

    private final FirstOrderPercentageDiscount discount = new FirstOrderPercentageDiscount();

    @Test
    void appliesForFirstOrderOnSmallAmount() {
        Order order = new Order();
        order.setFirstOrder(true);

        assertEquals(0, discount.apply(order, new BigDecimal("500.00"))
                .compareTo(new BigDecimal("450.00")));
    }

    @Test
    void appliesForFirstOrderOnLargeAmount() {
        Order order = new Order();
        order.setFirstOrder(true);

        assertEquals(0, discount.apply(order, new BigDecimal("2000.00"))
                .compareTo(new BigDecimal("1800.00")));
    }

    @Test
    void skipsForRegularOrder() {
        Order order = new Order();
        order.setFirstOrder(false);

        assertEquals(0, discount.apply(order, new BigDecimal("500.00"))
                .compareTo(new BigDecimal("500.00")));
    }
}