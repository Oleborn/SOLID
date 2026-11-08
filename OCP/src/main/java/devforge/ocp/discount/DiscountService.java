package devforge.ocp.discount;

import devforge.ocp.discount.inheritance.CombinedDiscount;
import devforge.ocp.discount.inheritance.Discount;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final Map<String, Discount> discounts;

    public DiscountService(List<Discount> discounts) {
        this.discounts = discounts.stream()
                .collect(Collectors.toMap(Discount::type, Function.identity()));
    }

    public BigDecimal calculate(Order order, String type) {
        BigDecimal total = sumItems(order);
        Discount discount = discounts.get(type);
        if (discount == null) {
            return total;
        }
        return discount.apply(order, total);
    }

    public BigDecimal applyCombined(Order order, List<String> types) {
        BigDecimal total = sumItems(order);
        List<Discount> chain = types.stream()
                .map(discounts::get)
                .filter(d -> d != null)
                .toList();
        return new CombinedDiscount(chain).apply(order, total);
    }

    private BigDecimal sumItems(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}