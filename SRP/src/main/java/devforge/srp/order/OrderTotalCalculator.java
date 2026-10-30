package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает итоговую сумму заказа с округлением по строкам.
 *
 * <p>Нюансы использования: класс общий для счёта клиенту и налогового отчёта,
 * потому что на текущий момент правила расчёта совпадают. Округление выполняется
 * по каждой строке отдельно, затем суммы складываются и приводятся к двум знакам.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class OrderTotalCalculator {

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