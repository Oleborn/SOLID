package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает сумму для налогового отчёта с округлением по документу.
 *
 * <p>Нюансы использования: цены позиций не округляются до умножения на количество,
 * округляется только итоговая сумма документа. Это правило установлено бухгалтерией
 * для налогового отчёта и не должно влиять на счёт клиенту.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class TaxReportCalculator {

    /**
     * Считает итоговую сумму для налогового отчёта.
     *
     * <p>Нюансы использования: порядок округления — по документу. Заказ должен быть
     * загружен внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма отчёта с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal calculate(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            BigDecimal lineTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}