package shop.itbook.itbookfront.delivery.adminapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.delivery.adminapi.adaptor.DeliveryAdaptor;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;

/**
 * Front 서버에서 관리자의 배송 관련 요청을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/deliveries")
public class DeliveryAdminController {

    private final GatewayConfig gatewayConfig;
    private final DeliveryAdaptor deliveryAdaptor;

    private final String DELIVERY_LIST_PATH = "/api/admin/deliveries";
    private final String DELIVERY_WAIT_LIST_PATH = "/api/admin/deliveries";

    @GetMapping
    public String adminDeliveryListPage(Model model) {
        ResponseEntity<CommonResponseBody<List<DeliveryWithStatusResponseDto>>> deliveryWaitList =
            deliveryAdaptor.getDeliveryWaitList(
                gatewayConfig.getGatewayServer() + DELIVERY_LIST_PATH);

        model.addAttribute("deliveryWaitList", deliveryWaitList.getBody().getResult());

        return "adminpage/delivery/admin-delivery-list";
    }

    @GetMapping("/wait")
    public String adminDeliveryWaitListPage(Model model) {
        ResponseEntity<CommonResponseBody<List<DeliveryWithStatusResponseDto>>> deliveryWaitList =
            deliveryAdaptor.getDeliveryWaitList(
                gatewayConfig.getGatewayServer() + DELIVERY_WAIT_LIST_PATH);

        model.addAttribute("deliveryWaitList", deliveryWaitList.getBody().getResult());

        return "adminpage/delivery/admin-delivery-list";
    }

    @GetMapping("/post")
    public String adminDeliveryListPost() {
        deliveryAdaptor.postDeliveryList(
            gatewayConfig.getGatewayServer() + DELIVERY_WAIT_LIST_PATH + "/post");

        return "redirect:/admin/deliveries";
    }
}
