package devforge.srp.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import devforge.srp.order.dto.TaxSummary;
import org.springframework.stereotype.Service;

/**
 * Рассчитывает НДС по заказу.
 *
 * <p>Нюансы использования: все методы реализуют одну согласованную налоговую
 * политику — цены в заказе указаны без НДС, ставка фиксированная, льгот нет,
 * округление стандартное (половина вверх до копеек). Если меняется ставка,
 * правило округления или появляется налоговая льгота, меняются все методы
 * этого класса вместе. Именно поэтому класс живёт одним файлом, а не дробится
 * на мелкие компоненты.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
public class TaxCalculator {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.20");

    /**
     * Считает все компоненты налогового расчёта за один вызов.
     *
     * <p>Нюансы использования: возвращает базу, НДС и итог с НДС. Все три
     * величины согласованы между собой и посчитаны по одной политике.</p>
     *
     * @param order заказ, для которого считается налог
     * @return налоговая база, НДС и итог с НДС
     * @author oleborn
     * @since 29.10.2026
     */
    public TaxSummary summarize(Order order) {
        BigDecimal base = taxBase(order);
        BigDecimal vat = calculateVatForBase(base);
        BigDecimal total = applyRounding(base.add(vat));

        return new TaxSummary(base, vat, total);
    }

    /**
     * Считает налоговую базу заказа — сумму цен без НДС.
     *
     * <p>Нюансы использования: суммирует базы всех позиций и округляет итог.
     * Порядок округления согласован с остальными методами класса.</p>
     *
     * @param order заказ, для которого считается база
     * @return налоговая база с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal taxBase(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            total = total.add(itemTaxBase(item));
        }

        return applyRounding(total);
    }

    /**
     * Считает сумму НДС по заказу.
     *
     * <p>Нюансы использования: берёт налоговую базу и применяет ставку через
     * {@link #calculateVatForBase(BigDecimal)}. Округление согласовано с базой.</p>
     *
     * @param order заказ, для которого считается НДС
     * @return сумма НДС с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal vat(Order order) {
        return calculateVatForBase(taxBase(order));
    }

    /**
     * Считает итог с НДС по заказу.
     *
     * <p>Нюансы использования: складывает базу и НДС, округляет итог по тому же
     * правилу, что и остальные величины.</p>
     *
     * @param order заказ, для которого считается итог
     * @return итог с НДС с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal totalWithVat(Order order) {
        return applyRounding(taxBase(order).add(vat(order)));
    }

    /**
     * Считает налоговую базу одной позиции.
     *
     * <p>Нюансы использования: цена умножается на количество и округляется по
     * стандартному правилу. Льготы не применяются, потому что в текущей политике
     * их нет.</p>
     *
     * @param item позиция заказа
     * @return налоговая база позиции с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal itemTaxBase(OrderItem item) {
        return applyRounding(
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }

    /**
     * Считает НДС одной позиции.
     *
     * <p>Нюансы использования: учитывает возможное освобождение через
     * {@link #isExempt(OrderItem)}. В текущей политике льгот нет, поэтому
     * освобождение всегда возвращает false, но точка расширения уже есть.</p>
     *
     * @param item позиция заказа
     * @return НДС позиции с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal itemVat(OrderItem item) {
        if (isExempt(item)) {
            return BigDecimal.ZERO;
        }

        return calculateVatForBase(itemTaxBase(item));
    }

    /**
     * Возвращает действующую ставку НДС.
     *
     * <p>Нюансы использования: ставка зафиксирована в одной константе. Если она
     * изменится, поменяется здесь, и это автоматически повлияет на все методы
     * класса.</p>
     *
     * @return ставка НДС
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal vatRate() {
        return VAT_RATE;
    }

    /**
     * Проверяет, освобождена ли позиция от НДС.
     *
     * <p>Нюансы использования: в текущей политике льгот нет. Метод существует
     * как точка расширения и часть той же налоговой политики — если льготы
     * появятся, они появятся здесь и повлияют на все остальные методы
     * согласованно.</p>
     *
     * @param item позиция заказа
     * @return true, если позиция освобождена от НДС
     * @author oleborn
     * @since 29.10.2026
     */
    public boolean isExempt(OrderItem item) {
        return false;
    }

    /**
     * Применяет к сумме действующее правило округления.
     *
     * <p>Нюансы использования: округление половины вверх до копеек. Это правило
     * используется во всех методах класса, чтобы результаты были согласованы.</p>
     *
     * @param value значение для округления
     * @return значение с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal applyRounding(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Считает НДС от произвольной базы по действующей ставке.
     *
     * <p>Нюансы использования: вспомогательный метод для {@link #vat(Order)},
     * {@link #itemVat(OrderItem)} и {@link #summarize(Order)}. Все три вызывают
     * его, чтобы не дублировать формулу и не разойтись в правилах.</p>
     *
     * @param base налоговая база
     * @return сумма НДС с масштабом 2
     * @author oleborn
     * @since 29.10.2026
     */
    public BigDecimal calculateVatForBase(BigDecimal base) {
        return applyRounding(base.multiply(VAT_RATE));
    }
}