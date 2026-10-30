package devforge.srp.order.dto;

import java.math.BigDecimal;

/**
 * Позиция заказа в ответе API.
 *
 * <p>Нюансы использования: содержит только данные, которые видит клиент; закупочная
 * цена в ответ не попадает.</p>
 *
 * @param productId идентификатор товара
 * @param quantity количество единиц товара
 * @param price цена продажи за единицу
 * @author oleborn
 * @since 29.10.2026
 */
public record OrderItemResponse(
    String productId,
    int quantity,
    BigDecimal price
) {
}
