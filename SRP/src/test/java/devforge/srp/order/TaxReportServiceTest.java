package devforge.srp.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * Юнит-тесты расчёта суммы для налогового отчёта без Spring-контекста.
 *
 * <p>Нюансы использования: тесты проверяют округление по документу. Суммы
 * сравниваются через {@code compareTo}, чтобы масштаб не влиял на результат.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
class TaxReportServiceTest {

    private final TaxReportService taxReportService =
            new TaxReportService(new TaxReportCalculator());

    /**
     * Проверяет округление итога документа, а не каждой строки.
     *
     * <p>Нюансы использования: 3.335 × 3 = 10.005, после округления итога — 10.01.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @Test
    void documentRounding() {
        Order order = new Order();

        order.addItem(item("SKU-1", new BigDecimal("3.335"), 3));

        BigDecimal result = taxReportService.generateReport(order);

        assertEquals(0, result.compareTo(new BigDecimal("10.01")));
    }

    /**
     * Создаёт позицию заказа для теста.
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