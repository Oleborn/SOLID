package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Начисляет бонусные баллы за заказ.
 *
 * <p>Нюансы использования: правила начисления появятся позже, сейчас сервис
 * только фиксирует факт начисления в логе.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class LoyaltyService {

    /**
     * Начисляет баллы за созданный заказ.
     *
     * @param order созданный заказ
     * @author oleborn
     * @since 29.10.2026
     */
    public void accruePoints(Order order) {
        log.info("Accruing loyalty points for order {}", order.getId());
    }
}