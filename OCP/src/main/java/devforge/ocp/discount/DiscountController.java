package devforge.ocp.discount;

import devforge.ocp.discount.dto.DiscountResponse;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ocp/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/calculate")
    public DiscountResponse calculate(@RequestBody Map<String, Object> body) {
        Order order = new Order();
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
        for (Map<String, Object> raw : items) {
            OrderItem item = new OrderItem();
            item.setProductId((String) raw.get("productId"));
            item.setPrice(new java.math.BigDecimal(raw.get("price").toString()));
            item.setQuantity(((Number) raw.get("quantity")).intValue());
            order.addItem(item);
        }
        return new DiscountResponse(discountService.calculate(order));
    }
}