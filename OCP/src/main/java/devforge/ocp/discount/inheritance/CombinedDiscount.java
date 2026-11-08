package devforge.ocp.discount.inheritance;

import devforge.ocp.order.Order;
import java.math.BigDecimal;
import java.util.List;

public class CombinedDiscount implements Discount {

    private final List<Discount> discounts;

    public CombinedDiscount(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String type() {
        return "COMBINED";
    }

    @Override
    public String description() {
        return "combined: " + discounts.stream().map(Discount::type).toList();
    }

    @Override
    public BigDecimal apply(Order order, BigDecimal total) {
        BigDecimal result = total;
        for (Discount discount : discounts) {
            result = discount.apply(order, result);
        }
        return result;
    }
}