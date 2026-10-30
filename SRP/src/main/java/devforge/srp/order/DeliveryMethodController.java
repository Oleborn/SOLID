package devforge.srp.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP-точки входа для справочника способов доставки.
 *
 * <p>Нюансы использования: контроллер возвращает сущности напрямую, без DTO.
 * Для этого маленького справочника такое решение приемлемо: три поля, всегда
 * меняются вместе, требований скрыть что-то пока нет. Если такие требования
 * появятся — здесь и будет введён DTO.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@RestController
@RequestMapping("/delivery-methods")
@RequiredArgsConstructor
public class DeliveryMethodController {

    private final DeliveryMethodRepository deliveryMethodRepository;

    /**
     * Возвращает все способы доставки.
     *
     * @return список способов доставки
     * @author oleborn
     * @since 29.10.2026
     */
    @GetMapping
    public List<DeliveryMethod> list() {
        return deliveryMethodRepository.findAll();
    }
}