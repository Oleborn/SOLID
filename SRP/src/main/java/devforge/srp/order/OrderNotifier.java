package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Отправляет уведомления клиенту после создания заказа.
 *
 * <p>Нюансы использования: подписан на событие создания заказа, получает его
 * после коммита транзакции. Сбой отправки логируется, но не откатывает заказ.
 * Проверка {@code FAIL_MAIL} нужна только для демонстрации в видео.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class OrderNotifier {

    /**
     * Отправляет письмо и SMS после успешного создания заказа.
     *
     * <p>Нюансы использования: вызывается после коммита. Ошибка отправки ловится
     * и логируется, чтобы не влиять на уже сохранённый заказ.</p>
     *
     * @param event событие создания заказа
     * @author oleborn
     * @since 29.10.2026
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderCreated(OrderCreatedEvent event) {
        try {
            Order order = event.order();

            for (OrderItem item : order.getItems()) {
                if ("FAIL_MAIL".equals(item.getProductId())) {
                    throw new RuntimeException("Mail server unavailable");
                }
            }

            log.info("Sending email for order {}", order.getId());
            log.info("Sending SMS for order {}", order.getId());
        } catch (RuntimeException e) {
            log.error("Failed to notify customer for order {}: {}",
                    event.order().getId(), e.getMessage());
        }
    }
}