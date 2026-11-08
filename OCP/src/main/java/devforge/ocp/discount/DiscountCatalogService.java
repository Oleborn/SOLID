package devforge.ocp.discount;

import java.util.List;

import devforge.ocp.discount.dto.DiscountTypeInfo;
import org.springframework.stereotype.Service;

@Service
public class DiscountCatalogService {

    public List<DiscountTypeInfo> list() {
        return List.of(
            describe("PERCENTAGE"),
            describe("FIXED"),
            describe("THRESHOLD"),
            describe("FIRST_ORDER")
        );
    }

    public DiscountTypeInfo describe(String type) {
        return switch (type) {
            case "PERCENTAGE" -> new DiscountTypeInfo("PERCENTAGE", "10% off everything");
            case "FIXED" -> new DiscountTypeInfo("FIXED", "500 off orders over 5000");
            case "THRESHOLD" -> new DiscountTypeInfo("THRESHOLD", "5% off orders over 1000");
            case "FIRST_ORDER" -> new DiscountTypeInfo("FIRST_ORDER", "10% off first order");
            case "NONE" -> new DiscountTypeInfo("NONE", "no discount");
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}