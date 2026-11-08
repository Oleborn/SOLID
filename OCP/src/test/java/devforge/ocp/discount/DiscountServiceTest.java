package devforge.ocp.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.discount.inheritance.CappedPercentageDiscount;
import devforge.ocp.discount.inheritance.FirstOrderPercentageDiscount;
import devforge.ocp.discount.inheritance.FixedDiscount;
import devforge.ocp.discount.inheritance.PercentageDiscount;
import devforge.ocp.discount.inheritance.ThresholdDiscount;
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
            new CappedPercentageDiscount(),
            new FirstOrderPercentageDiscount()
    ));

    @Test
    void delegatesToDiscountImplementation() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));

        assertEquals(0, service.calculate(order, "PERCENTAGE").compareTo(new BigDecimal("180.00")));
    }

    @Test
    void returnsTotalWhenTypeUnknown() {
        Order order = new Order();
        order.addItem(item("A", "100.00", 2));

        assertEquals(0, service.calculate(order, "NONE").compareTo(new BigDecimal("200.00")));
    }

    private OrderItem item(String productId, String price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(new BigDecimal(price));
        item.setQuantity(quantity);
        return item;
    }
}