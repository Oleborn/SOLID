package devforge.srp.order;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Считает сумму к оплате по заказу.
 *
 * <p>Нюансы использования: расчёт делегируется в {@link InvoiceCalculator}.
 * Сервис определяет, что для счёта клиенту нужна сумма, посчитанная по правилам
 * {@code InvoiceCalculator} — с округлением по строкам.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceCalculator invoiceCalculator;

    /**
     * Считает итоговую сумму заказа для счёта клиенту.
     *
     * <p>Нюансы использования: сумма совпадает с той, что считает
     * {@link InvoiceCalculator#calculate(Order)}. Заказ должен быть загружен
     * внутри транзакции из-за ленивой коллекции позиций.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal generateInvoice(Order order) {
        return invoiceCalculator.calculate(order);
    }
}