package devforge.ocp.discount.dto;

import java.util.List;

public record DiscountCombinedRequest(
    DiscountRequest order,
    List<String> types
) {
}