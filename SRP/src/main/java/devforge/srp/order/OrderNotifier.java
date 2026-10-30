package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Отправляет уведомления клиенту: письмо и SMS.
 *
 * <p>Нюансы использования: в текущей реализации просто пишет в лог. Реальная
 * отправка появится позже, когда будет настроен почтовый сервер.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class OrderNotifier {

    /**
     * Отправляет письмо и SMS по созданному заказу.
     *
     * @param order созданный заказ
     * @author oleborn
     * @since 29.10.2026
     */
    public void notifyCustomer(Order order) {
        for (OrderItem item : order.getItems()) {
            if ("FAIL_MAIL".equals(item.getProductId())) {
                throw new RuntimeException("Mail server unavailable");
            }
        }

        log.info("Sending email for order {}", order.getId());
        log.info("Sending SMS for order {}", order.getId());
    }
}