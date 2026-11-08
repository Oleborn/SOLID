package devforge.ocp.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DiscountServiceTest {

    private final DiscountService service = new DiscountService();

    @Test
    void returnsTotalWhenNoFlags() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, service.calculate(order, false, false, false, false)
                .compareTo(new BigDecimal("1000.00")));
    }

    @Test
    void appliesFirstOrderDiscount() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, service.calculate(order, true, false, false, false)
                .compareTo(new BigDecimal("900.00")));
    }

    @Test
    void appliesSecondItemDiscount() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 2));

        // sumItems: 1000.00 × 2 = 2000.00
        // secondItem (5%): 2000.00 × 0.95 = 1900.00
        assertEquals(0, service.calculate(order, false, true, false, false)
                .compareTo(new BigDecimal("1900.00")));
    }

    @Test
    void appliesPromoCodeDiscount() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, service.calculate(order, false, false, true, false)
                .compareTo(new BigDecimal("800.00")));
    }

    @Test
    void appliesHolidayDiscount() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, service.calculate(order, false, false, false, true)
                .compareTo(new BigDecimal("850.00")));
    }

    @Test
    void appliesFlagsInFixedOrder() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        // first (0.9) -> second (0.95) -> promo (-200) -> holiday (0.85)
        // 1000 -> 900 -> 855 -> 655 -> 556.75
        assertEquals(0, service.calculate(order, true, true, true, true)
                .compareTo(new BigDecimal("556.75")));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}