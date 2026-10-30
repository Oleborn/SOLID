package devforge.srp.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Создаёт и читает заказы.
 *
 * <p>Нюансы использования: сервис отвечает только за сохранение заказа и публикацию
 * события о его создании. Уведомления, баллы и аудит — это отдельные подписчики,
 * которые получают событие после коммита и не влияют на сохранение заказа.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Создаёт заказ, сохраняет его и публикует событие о создании.
     *
     * <p>Нюансы использования: событие публикуется внутри транзакции, подписчики
     * получают его после успешного коммита. Ошибка подписчика логируется, но не
     * откатывает заказ.</p>
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
        eventPublisher.publishEvent(new OrderCreatedEvent(saved));

        return saved;
    }

    /**
     * Ищет заказ по идентификатору.
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