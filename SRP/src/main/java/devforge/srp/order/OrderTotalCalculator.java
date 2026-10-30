package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * Считает итоговую сумму заказа с округлением по документу.
 *
 * <p>Нюансы использования: сумма считается по "сырым" ценам позиций, округление
 * выполняется один раз в конце — по итогу документа. Такой порядок требует
 * бухгалтерия для налогового отчёта.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class OrderTotalCalculator {

    /**
     * Считает итоговую сумму заказа с округлением итога до копеек.
     *
     * <p>Нюансы использования: цена позиции не округляется до умножения на
     * количество, округляется только итоговая сумма. Расчёт опирается на ленивую
     * коллекцию позиций, поэтому заказ должен быть загружен внутри транзакции.</p>
     *
     * @param order заказ, для которого считается сумма
     * @return итоговая сумма заказа с масштабом 2
     * @author oleborn
     * @since 30.10.2026
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