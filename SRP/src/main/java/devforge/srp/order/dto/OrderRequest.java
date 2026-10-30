package devforge.srp.order.dto;

import java.util.List;

/**
 * Запрос на создание заказа.
 *
 * <p>Нюансы использования: список позиций может быть пустым, тогда создаётся заказ
 * с нулевой суммой.</p>
 *
 * @param items позиции создаваемого заказа
 * @author oleborn
 * @since 29.10.2026
 */
public record OrderRequest(List<OrderItemRequest> items) {
}
