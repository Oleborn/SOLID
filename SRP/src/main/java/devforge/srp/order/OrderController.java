package devforge.srp.order;

import devforge.srp.order.dto.OrderRequest;
import devforge.srp.order.dto.OrderResponse;
import devforge.srp.order.dto.TaxReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * HTTP-точки входа для работы с заказами.
 *
 * <p>Нюансы использования: наружу отдаются только DTO, сущность {@link Order}
 * в ответы не попадает. Маппинг вынесен в {@link OrderMapper}, контроллер
 * занимается только приёмом запроса и выбором статуса ответа.</p>
 *
 * @author oleborn
 * @since 29.10.2026
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final TaxReportService taxReportService;
    private final OrderMapper orderMapper;

    /**
     * Создаёт заказ по запросу от администратора.
     *
     * @param request позиции создаваемого заказа
     * @return созданный заказ в виде DTO
     * @author oleborn
     * @since 29.10.2026
     */
    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @RequestBody OrderRequest request
    ) {
        Order order = orderService.createOrder(orderMapper.toItems(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toResponse(order));
    }

    /**
     * Возвращает заказ по идентификатору.
     *
     * <p>Нюансы использования: если заказа нет, отвечает 404 Not Found.
     * Наружу отдаётся {@link OrderResponse}, закупочная цена в ответ не попадает.</p>
     *
     * @param id идентификатор заказа
     * @return заказ в виде DTO
     * @author oleborn
     * @since 29.10.2026
     */
    @GetMapping("/{id}")
    public OrderResponse get(
            @PathVariable Long id
    ) {
        Order order = orderService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        return orderMapper.toResponse(order);
    }

    /**
     * Возвращает налоговый отчёт по заказу.
     *
     * @param id идентификатор заказа
     * @return отчёт с посчитанной суммой
     * @author oleborn
     * @since 29.10.2026
     */
    @GetMapping("/{id}/tax-report")
    public TaxReportResponse taxReport(
            @PathVariable Long id
    ) {
        Order order = orderService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        return new TaxReportResponse(order.getId(), taxReportService.generateReport(order));
    }
}