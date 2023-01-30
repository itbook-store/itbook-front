package shop.itbook.itbookfront.delivery.adminapi.controller;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.delivery.adminapi.adaptor.DeliveryAdaptor;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookfront.delivery.adminapi.exception.DeliveryNoWaitStatusException;
import shop.itbook.itbookfront.delivery.service.DeliveryService;

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

    private final DeliveryService deliveryService;

    /**
     * 관리자 페이지에서 모든 배송 정보를 상태와 함께 보여주는 페이지를 처리합니다.
     *
     * @param model 배송 리스트를 넣어서 View 에서 보여준다.
     * @return 관리자 모든 배송 정보 페이지.
     */
    @GetMapping
    public String adminDeliveryListPage(Model model) {
        List<DeliveryWithStatusResponseDto> deliveryWaitList =
            deliveryService.getDeliveryList();

        model.addAttribute("deliveryWaitList", deliveryWaitList);

        return "adminpage/delivery/admin-delivery-list";
    }

    /**
     * 관리자 페이지에서 배송 상태가 배송 대기인 상품들의 리스트만을 보여줍니다.
     *
     * @param model 배송 상태가 배송 대기중인 배송 정보 리스트를 넣어서 View 에 보여준다.
     * @return 배송 상태가 배송 대기중인 것만을 보여주는 페이지.
     */
    @GetMapping("/wait-list")
    public String adminDeliveryWaitListPage(Model model) {
        List<DeliveryWithStatusResponseDto> deliveryWaitList =
            deliveryService.getDeliveryWaitList();

        model.addAttribute("deliveryWaitList", deliveryWaitList);

        return "adminpage/delivery/admin-delivery-list";
    }

    /**
     * 배송 대기상태인 배송 정보들을 배송 서버에 등록 요청.
     *
     * @return 배송 등록 요청에 성공한 뒤 다시 리스트를 보여주기 위한 redirect 경로
     */
    @GetMapping("/registration")
    public String adminDeliveryListPost(RedirectAttributes redirectAttributes) {

        deliveryService.postDeliveryList(redirectAttributes);

        return "redirect:/admin/deliveries";
    }
}
