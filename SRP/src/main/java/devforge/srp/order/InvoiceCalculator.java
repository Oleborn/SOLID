package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает сумму для счёта клиенту с округлением по строкам.
 *
 * <p>Нюансы использования: цена каждой позиции округляется до копеек до умножения
 * на количество, затем строки суммируются. Это правило зафиксировано для счёта
 * клиенту и не должно меняться при изменении правил налогового отчёта.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class InvoiceCalculator {

    /**
     * Считает итоговую сумму для счёта клиенту.
     *
     * <p>Нюансы использования: порядок округления — по строкам. Заказ должен быть
     * загружен внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма счёта с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal calculate(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            BigDecimal unitPrice = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}