package devforge.ocp.discount.inheritance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class CombinedDiscountTest {

    @Test
    void percentageBeforeFixed() {
        Order order = new Order();
        order.addItem(item("A", "10000.00", 1));

        CombinedDiscount combined = new CombinedDiscount(List.of(
                new PercentageDiscount(),
                new FixedDiscount()
        ));

        assertEquals(0, combined.apply(order, new BigDecimal("10000.00"))
                .compareTo(new BigDecimal("8500.00")));
    }

    @Test
    void fixedBeforePercentage() {
        Order order = new Order();
        order.addItem(item("A", "10000.00", 1));

        CombinedDiscount combined = new CombinedDiscount(List.of(
                new FixedDiscount(),
                new PercentageDiscount()
        ));

        assertEquals(0, combined.apply(order, new BigDecimal("10000.00"))
                .compareTo(new BigDecimal("8550.00")));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}