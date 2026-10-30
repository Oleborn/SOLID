package devforge.srp.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Работа с заказами: создание и чтение.
 *
 * <p>Нюансы использования: сервис не считает суммы, расчёт живёт в
 * {@link InvoiceService}. Зависимости передаются через конструктор, который
 * генерирует Lombok.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Создаёт и сохраняет заказ с переданными позициями.
     *
     * <p>Нюансы использования: позиции сохраняются каскадом, отдельно их сохранять
     * не нужно. Метод выполняется в транзакции и возвращает уже сохранённый заказ
     * с проставленными идентификатором и датой создания.</p>
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

        return orderRepository.save(order);
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
