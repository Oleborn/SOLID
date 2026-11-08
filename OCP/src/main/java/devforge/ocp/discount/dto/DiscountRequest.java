package devforge.ocp.discount.dto;

import java.util.List;

public record DiscountRequest(
        List<DiscountItemRequest> items,
        String type,
        boolean firstOrder,
        String promoCode
) {
}