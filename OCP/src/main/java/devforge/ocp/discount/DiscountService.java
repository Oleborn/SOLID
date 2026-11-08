package devforge.ocp.discount;

import devforge.ocp.order.Order;
import devforge.ocp.order.OrderItem;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public BigDecimal calculate(Order order,
                                boolean isFirstOrder,
                                boolean isSecondItem,
                                boolean hasPromoCode,
                                boolean isHoliday) {
        BigDecimal total = sumItems(order);

        if (isFirstOrder) {
            total = total.multiply(new BigDecimal("0.9"));
        }
        if (isSecondItem) {
            total = total.multiply(new BigDecimal("0.95"));
        }
        if (hasPromoCode) {
            total = total.subtract(new BigDecimal("200"));
        }
        if (isHoliday) {
            total = total.multiply(new BigDecimal("0.85"));
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