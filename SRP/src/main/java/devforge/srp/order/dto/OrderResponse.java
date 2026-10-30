package devforge.srp.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Заказ в ответе API.
 *
 * <p>Нюансы использования: {@code total} — сумма, посчитанная сервисом счетов;
 * сама сущность заказа наружу не отдаётся.</p>
 *
 * @param id идентификатор заказа
 * @param items позиции заказа
 * @param total итоговая сумма заказа
 * @param createdAt дата создания заказа
 * @author oleborn
 * @since 29.10.2026
 */
public record OrderResponse(
    Long id,
    List<OrderItemResponse> items,
    BigDecimal total,
    Instant createdAt
) {
}
