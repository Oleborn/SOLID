package devforge.ocp.order.dto;

import java.math.BigDecimal;

public record TaxSummary(
    BigDecimal taxBase,
    BigDecimal vat,
    BigDecimal totalWithVat
) {
}