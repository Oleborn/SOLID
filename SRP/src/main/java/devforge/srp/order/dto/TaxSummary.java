package devforge.srp.order.dto;

import java.math.BigDecimal;

/**
 * Результат расчёта НДС по заказу.
 *
 * <p>Нюансы использования: содержит все компоненты налогового расчёта сразу,
 * чтобы вызывающий код не собирал их по частям. Все поля относятся к одной
 * налоговой политике и меняются вместе с ней.</p>
 *
 * @param taxBase налоговая база (сумма без НДС)
 * @param vat сумма НДС
 * @param totalWithVat итог с НДС
 * @author oleborn
 * @since 29.10.2026
 */
public record TaxSummary(
    BigDecimal taxBase,
    BigDecimal vat,
    BigDecimal totalWithVat
) {
}