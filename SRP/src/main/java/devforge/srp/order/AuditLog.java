package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Пишет запись в журнал аудита после создания заказа.
 *
 * <p>Нюансы использования: подписан на событие создания заказа, получает его
 * после коммита. Сбой записи логируется, но не откатывает заказ.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class AuditLog {

    /**
     * Записывает факт создания заказа в журнал аудита.
     *
     * @param event событие создания заказа
     * @author oleborn
     * @since 29.10.2026
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderCreated(OrderCreatedEvent event) {
        try {
            log.info("Audit: order {} created", event.order().getId());
        } catch (RuntimeException e) {
            log.error("Failed to write audit for order {}: {}",
                    event.order().getId(), e.getMessage());
        }
    }
}