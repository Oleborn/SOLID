package devforge.srp.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий способов доставки.
 *
 * @author oleborn
 * @since 29.10.2026
 */
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
}