package devforge.srp.order;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Считает сумму для налогового отчёта.
 *
 * <p>Нюансы использования: расчёт делегируется в {@link OrderTotalCalculator}.
 * На текущий момент правила расчёта в отчёте и в счёте клиенту совпадают, поэтому
 * оба сервиса используют один калькулятор.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class TaxReportService {

    private final OrderTotalCalculator orderTotalCalculator;

    /**
     * Считает сумму заказа для налогового отчёта.
     *
     * <p>Нюансы использования: сумма совпадает с той, что считает
     * {@link OrderTotalCalculator#calculate(Order)}. Заказ должен быть загружен
     * внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal generateReport(Order order) {
        return orderTotalCalculator.calculate(order);
    }
}