package devforge.srp.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Тесты сохранения и чтения заказов на реальном JPA-контексте.
 *
 * <p>Нюансы использования: каждый тест выполняется в транзакции и откатывается,
 * поэтому данные в H2 не накапливаются. Сервис больше не зависит от уведомлений,
 * баллов и аудита — они подписаны на событие и не участвуют в сохранении заказа.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    /**
     * Проверяет каскадное сохранение заказа и последующее чтение по идентификатору.
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @Test
    void createOrderAndFindIt() {
        OrderItem first = item("SKU-1", new BigDecimal("3.335"), 3);
        OrderItem second = item("SKU-2", new BigDecimal("5.50"), 1);

        Order saved = orderService.createOrder(List.of(first, second));

        assertNotNull(saved.getId());
        assertEquals(2, saved.getItems().size());
        assertNotNull(saved.getCreatedAt());

        Optional<Order> found = orderService.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals(2, found.get().getItems().size());
    }

    private OrderItem item(
            String productId,
            BigDecimal price,
            int quantity
    ) {
        OrderItem item = new OrderItem();

        item.setProductId(productId);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setPurchasePrice(price);

        return item;
    }
}