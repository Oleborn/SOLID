package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает сумму к оплате по заказу.
 *
 * <p>Нюансы использования: округление выполняется по каждой строке отдельно, поэтому
 * итог может отличаться от округления суммы "сырых" значений.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class InvoiceService {

    /**
     * Считает итоговую сумму заказа с округлением цены каждой позиции до копеек.
     *
     * <p>Нюансы использования: цена позиции округляется до двух знаков до умножения
     * на количество, итог тоже приводится к двум знакам. Расчёт опирается на ленивую
     * коллекцию позиций, поэтому заказ должен быть загружен внутри транзакции.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal generateInvoice(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            BigDecimal unitPrice = item.getPrice().setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
