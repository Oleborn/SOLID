package devforge.ocp.discount;

import devforge.ocp.discount.dto.DiscountRequest;
import devforge.ocp.discount.dto.DiscountResponse;
import devforge.ocp.discount.dto.DiscountTypeInfo;
import devforge.ocp.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocp/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountRequestMapper requestMapper;
    private final DiscountCatalogService catalogService;

    @PostMapping("/calculate")
    public DiscountResponse calculate(@RequestBody DiscountRequest request) {
        Order order = requestMapper.toOrder(request);
        return new DiscountResponse(discountService.calculate(order, request.type()));
    }

    @GetMapping("/types")
    public List<DiscountTypeInfo> types() {
        return catalogService.list();
    }
}