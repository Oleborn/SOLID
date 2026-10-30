package devforge.srp.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Заказ интернет-магазина.
 *
 * <p>Нюансы использования: позиции добавляются только через {@link #addItem(OrderItem)},
 * чтобы обратная ссылка {@code OrderItem.order} всегда была заполнена и каскадное
 * сохранение работало. Дата создания проставляется автоматически при сохранении.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private Instant createdAt;

    /**
     * Добавляет позицию в заказ и связывает её с текущим заказом.
     *
     * <p>Нюансы использования: метод не сохраняет заказ в базе, сохранение выполняет
     * репозиторий. Повторный вызов с той же позицией добавит её в список дважды.</p>
     *
     * @param item позиция заказа, которая добавляется в список
     * @author oleborn
     * @since 29.10.2026
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    /**
     * Проставляет дату создания заказа перед вставкой в базу, если она ещё не задана.
     *
     * <p>Нюансы использования: вызывается Hibernate, вручную не вызывать. Уже заданная
     * дата не перезаписывается.</p>
     *
     * @author oleborn
     * @since 29.10.2026
     */
    @PrePersist
    void fillCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
