package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает сумму для налогового отчёта.
 *
 * <p>Нюансы использования: правила расчёта совпадают с теми, что использует
 * {@link InvoiceService}, поэтому формула повторена здесь. Округление выполняется
 * по каждой строке отдельно, итог приводится к двум знакам.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class TaxReportService {

    /**
     * Считает сумму заказа для налогового отчёта.
     *
     * <p>Нюансы использования: сумма совпадает с той, что считает
     * {@link InvoiceService#generateInvoice(Order)}. Заказ должен быть загружен
     * внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal generateReport(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            BigDecimal unitPrice = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}