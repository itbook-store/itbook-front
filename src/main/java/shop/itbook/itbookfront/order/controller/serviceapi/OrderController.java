package shop.itbook.itbookfront.order.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderProductDetailResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookfront.order.service.OrderService;

/**
 * Front 서버에서 사용자의 주문 관련 요청을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 마이페이지에서 해당 회원의 주문 목록을 불러옵니다.
     *
     * @return 마이페이지 안의 주문 리스트 페이지
     */
    @GetMapping("/mypage/list")
    public String myOrderDeliveryPage(@PageableDefault Pageable pageable,
                                      @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                      Model model) {

        PageResponse<OrderListMemberViewResponseDto> pageResponse =
            orderService.findOrderListOfMemberPageResponse(pageable, userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/orders/mypage/list");

        return "mypage/order/my-order-list";
    }

    /**
     * 주문 완료 처리를 위한 컨트롤러.
     * 로직을 다 처리한 후 완료 페이지를 반환한다.
     *
     * @return 사용자에게 보여줄 주문 완료페이지
     */
    @GetMapping("/completion/{orderNo}")
    public String orderCompletion(@PathVariable("orderNo") Long orderNo, Model model) {

        OrderDetailsResponseDto orderDetails = orderService.findOrderDetails(orderNo);

        Long totalProductPrice = orderDetails.getOrderProductDetailResponseDtoList().stream()
            .mapToLong(OrderProductDetailResponseDto::getProductPrice).sum();

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("totalProductPrice", totalProductPrice);

        return "mainpage/order/orderDetailsForm";
    }

    @GetMapping("/details/{orderNo}")
    public String orderDetailsView(@PathVariable("orderNo") Long orderNo, Model model) {

        OrderDetailsResponseDto orderDetails = orderService.findOrderDetails(orderNo);

        Long totalProductPrice = orderDetails.getOrderProductDetailResponseDtoList().stream()
            .mapToLong(OrderProductDetailResponseDto::getProductPrice).sum();

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("totalProductPrice", totalProductPrice);

        return "mypage/order/orderDetailsForm";
    }

    @GetMapping("/mypage/details-sub/{orderNo}")
    public String orderSubscriptionDetails(@PathVariable Long orderNo, Model model) {

        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsList =
            orderService.findOrderSubscriptionDetails(orderNo);

        model.addAttribute("detailsList", orderSubscriptionDetailsList);

        return "mypage/order/orderSubDetailsForm";
    }

    @GetMapping("/mypage/list/subscription")
    public String subscriptionOrderListByMember(@PageableDefault Pageable pageable,
                                                @AuthenticationPrincipal
                                                UserDetailsDto userDetailsDto,
                                                Model model) {

        PageResponse<OrderSubscriptionListDto> pageResponse =
            orderService.orderSubscriptionListByMember(pageable, userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/orders/mypage/list/subscription");

        return "mypage/order/my-subscription-list";
    }
}
