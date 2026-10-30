package devforge.srp.order;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Считает сумму для налогового отчёта.
 *
 * <p>Нюансы использования: расчёт делегируется в {@link TaxReportCalculator}.
 * Сервис определяет, что для налогового отчёта нужна сумма, посчитанная по правилам
 * {@code TaxReportCalculator} — с округлением по документу.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class TaxReportService {

    private final TaxReportCalculator taxReportCalculator;

    /**
     * Считает сумму заказа для налогового отчёта.
     *
     * <p>Нюансы использования: сумма совпадает с той, что считает
     * {@link TaxReportCalculator#calculate(Order)}. Заказ должен быть загружен
     * внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal generateReport(Order order) {
        return taxReportCalculator.calculate(order);
    }
}