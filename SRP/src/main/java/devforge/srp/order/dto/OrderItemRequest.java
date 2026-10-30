package devforge.srp.order.dto;

import java.math.BigDecimal;

/**
 * Позиция заказа во входящем запросе.
 *
 * <p>Нюансы использования: {@code purchasePrice} приходит от склада, потому что заказ
 * создаёт администратор, а не клиент.</p>
 *
 * @param productId идентификатор товара
 * @param price цена продажи за единицу
 * @param quantity количество единиц товара
 * @param purchasePrice закупочная цена за единицу
 * @author oleborn
 * @since 29.10.2026
 */
public record OrderItemRequest(
    String productId,
    BigDecimal price,
    int quantity,
    BigDecimal purchasePrice
) {
}
