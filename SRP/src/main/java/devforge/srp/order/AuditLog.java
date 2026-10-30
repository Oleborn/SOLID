package devforge.srp.order;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Пишет запись в журнал аудита по созданному заказу.
 *
 * <p>Нюансы использования: журнал нужен бухгалтерии. Формат записи появится позже,
 * сейчас сервис только фиксирует факт создания заказа.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@Slf4j
public class AuditLog {

    /**
     * Записывает в журнал аудита факт создания заказа.
     *
     * @param order созданный заказ
     * @author oleborn
     * @since 29.10.2026
     */
    public void writeOrderCreated(Order order) {
        log.info("Audit: order {} created", order.getId());
    }
}