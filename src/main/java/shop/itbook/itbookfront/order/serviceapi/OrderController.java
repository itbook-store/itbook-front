package shop.itbook.itbookfront.order.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.request.OrderProductRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaperResponseDto;

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
     * 상품 주문시 해당 상품의 정보를 불러옵니다.
     *
     * @param productNo the product no
     * @return 주문 작성 페이지
     */
    @GetMapping("/sheet")
    public String orderProduct(@RequestParam(value = "productNo") Long productNo,
                               Model model) {

        UriComponents uriComponents =
            UriComponentsBuilder.fromUriString(gatewayConfig.getGatewayServer()).path("/api/orders")
                .queryParam("productNo", productNo)
                .build();

        // TODO: 2023/01/27 현재 로그인한 회원정보가 담겨야함. 

        
        OrderProductRequestDto orderProductRequestDto =
            new OrderProductRequestDto(productNo);

        HttpEntity<OrderProductRequestDto> http = new HttpEntity<>(orderProductRequestDto);

        List<OrderPaperResponseDto> orderProduct =
            orderAdaptor.findOrderProductList(uriComponents.toUri(), http);

        model.addAttribute("orderProduct", orderProduct);

        return "mainpage/order/order-write";
    }
}
