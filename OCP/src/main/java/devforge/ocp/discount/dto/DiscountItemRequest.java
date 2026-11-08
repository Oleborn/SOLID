package devforge.ocp.discount.dto;

import java.math.BigDecimal;

public record DiscountItemRequest(
    String productId,
    BigDecimal price,
    int quantity
) {
}