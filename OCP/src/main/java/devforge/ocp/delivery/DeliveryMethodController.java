package devforge.ocp.delivery;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocp/delivery-methods")
@RequiredArgsConstructor
public class DeliveryMethodController {

    private final DeliveryMethodRepository deliveryMethodRepository;

    @GetMapping
    public List<DeliveryMethod> list() {
        return deliveryMethodRepository.findAll();
    }
}