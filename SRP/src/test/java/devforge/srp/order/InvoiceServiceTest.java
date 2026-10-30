package devforge.srp.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * Юнит-тесты расчёта суммы заказа без Spring-контекста.
 *
 * <p>Нюансы использования: тесты проверяют именно округление по строкам, поэтому
 * суммы сравниваются через {@code compareTo}, чтобы масштаб не влиял на результат.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
class InvoiceServiceTest {

    private final InvoiceService invoiceService =
            new InvoiceService(new InvoiceCalculator());

    /**
     * Проверяет округление цены позиции до умножения на количество.
     *
     * <p>Нюансы использования: 3.335 округляется до 3.34, итог по строке — 10.02.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @Test
    void perLineRounding() {
        Order order = new Order();

        order.addItem(item("SKU-1", new BigDecimal("3.335"), 3));

        BigDecimal result = invoiceService.generateInvoice(order);

        assertEquals(0, result.compareTo(new BigDecimal("10.02")));
    }

    /**
     * Проверяет суммирование нескольких позиций заказа.
     *
     * <p>Нюансы использования: 10.00 × 2 плюс 5.50 × 1 даёт 25.50.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @Test
    void sumOfTwoItems() {
        Order order = new Order();

        order.addItem(item("SKU-1", new BigDecimal("10.00"), 2));
        order.addItem(item("SKU-2", new BigDecimal("5.50"), 1));

        BigDecimal result = invoiceService.generateInvoice(order);

        assertEquals(0, result.compareTo(new BigDecimal("25.50")));
    }

    /**
     * Создаёт позицию заказа для теста.
     *
     * <p>Нюансы использования: позиция не сохраняется в базе, она нужна только для
     * расчёта в памяти.</p>
     *
     * @param productId идентификатор товара
     * @param price цена продажи за единицу
     * @param quantity количество единиц товара
     * @return подготовленная позиция заказа
     * @author oleborn
     * @since 29.10.2026
     */
    private OrderItem item(
        String productId,
        BigDecimal price,
        int quantity
    ) {
        OrderItem item = new OrderItem();

        item.setProductId(productId);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setPurchasePrice(price);

        return item;
    }
}
