package devforge.srp.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий заказов.
 *
 * <p>Нюансы использования: {@link #findById(Object)} переопределён, чтобы
 * загружать позиции заказа одним запросом. Без этого при возврате сущности
 * напрямую в API возникает {@code LazyInitializationException}.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Ищет заказ и сразу загружает его позиции.
     *
     * @param id идентификатор заказа
     * @return заказ с загруженными позициями
     * @author oleborn
     * @since 29.10.2026
     */
    @Override
    @EntityGraph(attributePaths = "items")
    Optional<Order> findById(Long id);
}