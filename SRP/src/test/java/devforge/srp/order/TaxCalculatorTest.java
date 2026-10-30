package devforge.srp.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import devforge.srp.order.dto.TaxSummary;
import org.junit.jupiter.api.Test;

/**
 * Юнит-тесты налогового расчёта.
 *
 * <p>Нюансы использования: тесты проверяют, что все методы класса реализуют
 * одну согласованную политику. Они не доказывают, что будущие изменения
 * будут затрагивать методы совместно — это свойство текущей политики,
 * а не гарантия на будущее.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
class TaxCalculatorTest {

    private final TaxCalculator taxCalculator = new TaxCalculator();

    /**
     * Проверяет расчёт НДС для одной позиции.
     *
     * <p>Нюансы использования: база 100.00, НДС 20.00, итог 120.00.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @Test
    void singleItem() {
        Order order = new Order();
        order.addItem(item("SKU-1", new BigDecimal("100.00"), 1));

        TaxSummary summary = taxCalculator.summarize(order);

        assertEquals(0, summary.taxBase().compareTo(new BigDecimal("100.00")));
        assertEquals(0, summary.vat().compareTo(new BigDecimal("20.00")));
        assertEquals(0, summary.totalWithVat().compareTo(new BigDecimal("120.00")));
    }

    /**
     * Проверяет расчёт для нескольких позиций.
     *
     * <p>Нюансы использования: база 130.00, НДС 26.00, итог 156.00.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
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

    private OrderItem item(String productId, BigDecimal price, int quantity) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setPurchasePrice(price);
        return item;
    }
}