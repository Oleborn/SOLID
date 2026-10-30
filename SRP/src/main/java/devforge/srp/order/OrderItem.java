package devforge.srp.order;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Позиция заказа: один товар с ценой продажи, закупочной ценой и количеством.
 *
 * <p>Нюансы использования: {@code purchasePrice} — внутренние данные для бухгалтерии,
 * клиенту не показываются. Связь с заказом ленивая, поэтому обращаться к ней нужно
 * внутри транзакции.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productId;

    private BigDecimal price;

    private int quantity;

    private BigDecimal purchasePrice;
}
