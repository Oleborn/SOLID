package devforge.ocp.discount;

import devforge.ocp.discount.dto.DiscountTypeInfo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DiscountCatalogService {

    private final DiscountService discountService;

    public DiscountCatalogService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public List<DiscountTypeInfo> list() {
        return discountService.availableTypes().stream()
                .map(this::toInfo)
                .toList();
    }

    public DiscountTypeInfo toInfo(String type) {
        return new DiscountTypeInfo(type, discountService.descriptionOf(type));
    }
}