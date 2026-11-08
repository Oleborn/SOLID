package devforge.ocp.discount;

import devforge.ocp.discount.dto.DiscountRequest;
import devforge.ocp.discount.dto.DiscountResponse;
import devforge.ocp.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ocp/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountRequestMapper requestMapper;

    @PostMapping("/calculate")
    public DiscountResponse calculate(@RequestBody DiscountRequest request) {
        Order order = requestMapper.toOrder(request);
        return new DiscountResponse(discountService.calculate(order));
    }
}