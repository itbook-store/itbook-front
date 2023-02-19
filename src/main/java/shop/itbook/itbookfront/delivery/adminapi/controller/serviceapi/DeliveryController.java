package shop.itbook.itbookfront.delivery.adminapi.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.delivery.service.DeliveryService;

/**
 * 사용자의 배송 관련 요청을 받아 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/completion/{deliveryNo}")
    public String deliveryStatusCompletion(@PathVariable Long deliveryNo) {

        deliveryService.completeDeliveryStatus(deliveryNo);

        return "redirect:#";
    }
}
