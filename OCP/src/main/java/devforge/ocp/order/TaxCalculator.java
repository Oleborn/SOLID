package devforge.ocp.order;

import devforge.ocp.order.dto.TaxSummary;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculator {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.20");

    public TaxSummary summarize(Order order) {
        BigDecimal base = taxBase(order);
        BigDecimal vat = calculateVatForBase(base);
        BigDecimal total = applyRounding(base.add(vat));
        return new TaxSummary(base, vat, total);
    }

    public BigDecimal taxBase(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(itemTaxBase(item));
        }
        return applyRounding(total);
    }

    public BigDecimal vat(Order order) {
        return calculateVatForBase(taxBase(order));
    }

    public BigDecimal totalWithVat(Order order) {
        return applyRounding(taxBase(order).add(vat(order)));
    }

    public BigDecimal itemTaxBase(OrderItem item) {
        return applyRounding(
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }

    public BigDecimal itemVat(OrderItem item) {
        if (isExempt(item)) {
            return BigDecimal.ZERO;
        }
        return calculateVatForBase(itemTaxBase(item));
    }

    public BigDecimal vatRate() {
        return VAT_RATE;
    }

    public boolean isExempt(OrderItem item) {
        return false;
    }

    public BigDecimal applyRounding(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateVatForBase(BigDecimal base) {
        return applyRounding(base.multiply(VAT_RATE));
    }
}