package devforge.ocp.discount;

import devforge.ocp.discount.strategy.DiscountStrategy;
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

    private final Map<String, DiscountStrategy> strategies;

    public DiscountService(List<DiscountStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(DiscountStrategy::type, Function.identity()));
    }

    public BigDecimal calculate(Order order, String type) {
        BigDecimal total = sumItems(order);
        DiscountStrategy strategy = strategies.get(type);
        if (strategy == null) {
            return total;
        }
        return strategy.apply(order, total);
    }

    public boolean supportsType(String type) {
        return strategies.containsKey(type);
    }

    public List<String> availableTypes() {
        return strategies.values().stream()
                .map(DiscountStrategy::type)
                .toList();
    }

    public String descriptionOf(String type) {
        DiscountStrategy strategy = strategies.get(type);
        return strategy != null ? strategy.description() : null;
    }

    private BigDecimal sumItems(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}