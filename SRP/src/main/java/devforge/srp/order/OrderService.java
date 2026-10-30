package devforge.srp.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Работа с заказами: создание и чтение.
 *
 * <p>Нюансы использования: при создании заказа сервис не только сохраняет его,
 * но и вызывает уведомления, начисление баллов и запись в журнал аудита. Все
 * эти действия выполняются внутри одного метода и одной транзакции.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderNotifier orderNotifier;
    private final LoyaltyService loyaltyService;
    private final AuditLog auditLog;

    /**
     * Создаёт заказ, сохраняет его и выполняет связанные действия.
     *
     * <p>Нюансы использования: после сохранения вызываются уведомления, начисление
     * баллов и аудит. Все вызовы происходят в одной транзакции, поэтому ошибка
     * любого из них откатит и сам заказ.</p>
     *
     * @param items позиции будущего заказа
     * @return сохранённый заказ
     * @author oleborn
     * @since 29.10.2026
     */
    @Transactional
    public Order createOrder(List<OrderItem> items) {
        Order order = new Order();

        for (OrderItem item : items) {
            order.addItem(item);
        }

        Order saved = orderRepository.save(order);

        log.warn("Support: overriding email channel with SMS for order {}", saved.getId());
        orderNotifier.notifyCustomer(saved);
        loyaltyService.accruePoints(saved);
        auditLog.writeOrderCreated(saved);

        return saved;
    }

    /**
     * Ищет заказ по идентификатору.
     *
     * <p>Нюансы использования: метод не открывает транзакцию, поэтому ленивая
     * коллекция позиций доступна только в том случае, если заказ загружен
     * внутри вызывающей транзакции.</p>
     *
     * @param id идентификатор заказа
     * @return заказ, если он существует
     * @author oleborn
     * @since 29.10.2026
     */
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}