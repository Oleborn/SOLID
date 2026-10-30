package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Начисляет бонусные баллы после создания заказа.
 *
 * <p>Нюансы использования: подписан на событие создания заказа, получает его
 * после коммита. Сбой начисления логируется, но не откатывает заказ.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class LoyaltyService {

    /**
     * Начисляет баллы после успешного создания заказа.
     *
     * @param event событие создания заказа
     * @author oleborn
     * @since 29.10.2026
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderCreated(OrderCreatedEvent event) {
        try {
            log.info("Accruing loyalty points for order {}", event.order().getId());
        } catch (RuntimeException e) {
            log.error("Failed to accrue points for order {}: {}",
                    event.order().getId(), e.getMessage());
        }
    }
}