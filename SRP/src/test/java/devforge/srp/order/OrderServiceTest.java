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
 * поэтому данные в H2 не накапливаются.</p>
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
     * <p>Нюансы использования: позиции не сохраняются отдельно, их сохраняет каскад
     * вместе с заказом.</p>
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

    /**
     * Создаёт позицию заказа для теста.
     *
     * <p>Нюансы использования: позиция не сохраняется в базе, её сохранит каскад
     * при создании заказа.</p>
     *
     * @param productId идентификатор товара
     * @param price цена продажи за единицу
     * @param quantity количество единиц товара
     * @return подготовленная позиция заказа
     * @author oleborn
     * @since 29.10.2026
     */
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
