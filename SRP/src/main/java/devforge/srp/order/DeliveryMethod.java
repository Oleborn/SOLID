package devforge.srp.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Способ доставки.
 *
 * <p>Нюансы использования: маленький справочник из трёх полей. Отдаётся и в базу,
 * и в API одним и тем же классом, потому что все три поля всегда меняются вместе
 * и конфликта между требованиями пока нет. Если появится требование скрыть поле
 * или изменить формат ответа — решение о разделении можно будет принять тогда,
 * а не заранее.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@Entity
@Table(name = "delivery_methods")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private int deliveryDays;
}