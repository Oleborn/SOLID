package devforge.ocp.discount;

import devforge.ocp.discount.rule.DiscountRule;
import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final List<DiscountRule> rules;

    public DiscountService(List<DiscountRule> rules) {
        this.rules = rules.stream()
                .sorted(Comparator.comparingInt(DiscountRule::priority))
                .toList();
    }

    public BigDecimal calculate(Order order) {
        BigDecimal total = sumItems(order);
        for (DiscountRule rule : rules) {
            if (rule.isApplicable(order)) {
                total = rule.apply(order, total);
            }
        }
        return total;
    }

    private BigDecimal sumItems(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}