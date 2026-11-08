package devforge.ocp.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import devforge.ocp.discount.strategy.FirstOrderDiscount;
import devforge.ocp.discount.strategy.FixedDiscount;
import devforge.ocp.discount.strategy.PercentageDiscount;
import devforge.ocp.discount.strategy.ThresholdDiscount;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscountServiceTest {

    private final DiscountService service = new DiscountService(List.of(
            new PercentageDiscount(),
            new FixedDiscount(),
            new ThresholdDiscount(),
            new FirstOrderDiscount()
    ));

    @Test
    void supportsKnownTypes() {
        assertTrue(service.supportsType("PERCENTAGE"));
        assertTrue(service.supportsType("FIXED"));
        assertTrue(service.supportsType("THRESHOLD"));
        assertTrue(service.supportsType("FIRST_ORDER"));
        assertFalse(service.supportsType("UNKNOWN"));
    }

    @Test
    void delegatesToStrategy() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));

        assertEquals(0, service.calculate(order, "PERCENTAGE").compareTo(new BigDecimal("180.00")));
    }

    @Test
    void returnsTotalWithoutDiscountWhenTypeUnknown() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));

        assertEquals(0, service.calculate(order, "NONE").compareTo(new BigDecimal("200.00")));
        assertEquals(0, service.calculate(order, "UNKNOWN").compareTo(new BigDecimal("200.00")));
    }

    @Test
    void describesKnownTypes() {
        assertEquals("10% off everything", service.descriptionOf("PERCENTAGE"));
        assertEquals("500 off orders over 5000", service.descriptionOf("FIXED"));
        assertNull(service.descriptionOf("UNKNOWN"));
    }

    @Test
    void listsAvailableTypes() {
        assertEquals(4, service.availableTypes().size());
        assertTrue(service.availableTypes().contains("PERCENTAGE"));
        assertTrue(service.availableTypes().contains("FIRST_ORDER"));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}