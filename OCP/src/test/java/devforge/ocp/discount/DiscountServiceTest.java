package devforge.ocp.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DiscountServiceTest {

    private final DiscountService service = new DiscountService();

    @Test
    void sumsItemsWithoutDiscount() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));
        order.addItem(item("B", "50.00", 1));

        assertEquals(0, service.calculate(order, "NONE").compareTo(new BigDecimal("250.00")));
    }

    @Test
    void appliesPercentageDiscount() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));

        assertEquals(0, service.calculate(order, "PERCENTAGE").compareTo(new BigDecimal("180.00")));
    }

    @Test
    void appliesFixedDiscountWhenThresholdReached() {
        Order order = new Order();
        order.addItem(item("A", "6000.00", 1));

        assertEquals(0, service.calculate(order, "FIXED").compareTo(new BigDecimal("5500.00")));
    }

    @Test
    void skipsFixedDiscountBelowThreshold() {
        Order order = new Order();
        order.addItem(item("A", "1000.00", 1));

        assertEquals(0, service.calculate(order, "FIXED").compareTo(new BigDecimal("1000.00")));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}