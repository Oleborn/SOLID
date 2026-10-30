package devforge.srp.order.dto;

import java.math.BigDecimal;

/**
 * Налоговый отчёт по заказу в ответе API.
 *
 * <p>Нюансы использования: содержит только сумму, посчитанную для отчёта.
 * В дальнейшем сюда могут добавляться налоговые ставки и разбивка по позициям.</p>
 *
 * @param orderId идентификатор заказа
 * @param total итоговая сумма для налогового отчёта
 * @author oleborn
 * @since 29.10.2026
 */
public record TaxReportResponse(
    Long orderId,
    BigDecimal total
) {
}