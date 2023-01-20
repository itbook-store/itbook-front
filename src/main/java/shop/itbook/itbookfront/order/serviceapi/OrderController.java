package shop.itbook.itbookfront.order.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.response.OrderProductResponseDto;

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

    private final GatewayConfig gatewayConfig;
    private final OrderAdaptor orderAdaptor;

    /**
     * 마이페이지에서 해당 회원의 주문 목록을 불러옵니다.
     *
     * @return 마이페이지 안의 주문 리스트 페이지
     */
    @GetMapping("/mypage/order-list")
    public String myOrderDeliveryPage() {
        return "mypage/order/my-order-list";
    }

    /**
     * 개별 상품 주문시 해당 상품의 정보를 불러옵니다.
     *
     * @param productNo the product no
     * @return 주문 작성 페이지
     */
    @GetMapping("/write")
    public String orderProduct(@RequestParam("productNo") Long productNo, Model model) {

        OrderProductResponseDto orderProduct =
            orderAdaptor.findOrderProductList(gatewayConfig.getGatewayServer() +  );

        model.addAttribute("orderProduct", )
        return "mainpage/order/order-write";
    }
}
