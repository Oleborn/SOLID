package devforge.ocp.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import devforge.ocp.order.dto.TaxSummary;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TaxCalculatorTest {

    private final TaxCalculator taxCalculator = new TaxCalculator();

    @Test
    void singleItem() {
        Order order = new Order();
        order.addItem(item("SKU-1", new BigDecimal("100.00"), 1));

        TaxSummary summary = taxCalculator.summarize(order);

        assertEquals(0, summary.taxBase().compareTo(new BigDecimal("100.00")));
        assertEquals(0, summary.vat().compareTo(new BigDecimal("20.00")));
        assertEquals(0, summary.totalWithVat().compareTo(new BigDecimal("120.00")));
    }

    @Test
    void multipleItems() {
        Order order = new Order();
        order.addItem(item("SKU-1", new BigDecimal("50.00"), 2));
        order.addItem(item("SKU-2", new BigDecimal("30.00"), 1));

        TaxSummary summary = taxCalculator.summarize(order);

        assertEquals(0, summary.taxBase().compareTo(new BigDecimal("130.00")));
        assertEquals(0, summary.vat().compareTo(new BigDecimal("26.00")));
        assertEquals(0, summary.totalWithVat().compareTo(new BigDecimal("156.00")));
    }

    @Test
    void vatRateIsConsistent() {
        assertEquals(0, taxCalculator.vatRate().compareTo(new BigDecimal("0.20")));
    }

    @Test
    void noItemsExempt() {
        Order order = new Order();
        OrderItem item = item("SKU-1", new BigDecimal("100.00"), 1);
        order.addItem(item);

        assertEquals(false, taxCalculator.isExempt(item));
    }

    private OrderItem item(String productId, BigDecimal price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(price);
        item.setQuantity(quantity);
        return item;
    }
}