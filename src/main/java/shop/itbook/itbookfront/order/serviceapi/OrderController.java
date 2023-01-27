package shop.itbook.itbookfront.order.serviceapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.request.OrderProductRequestDto;
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
     * 상품 주문시 해당 상품의 정보를 불러옵니다.
     *
     * @param productNo the product no
     * @return 주문 작성 페이지
     */
    @GetMapping("/write")
    public String orderProduct(@RequestParam(value = "productNo", required = false) Long productNo,
                               Model model) {

        UriComponents uriComponents =
            UriComponentsBuilder.fromUriString(gatewayConfig.getGatewayServer()).path("/api/orders")
                .build();

        OrderProductRequestDto orderProductRequestDto =
            new OrderProductRequestDto(Optional.of(productNo));

        HttpEntity<OrderProductRequestDto> http = new HttpEntity<>(orderProductRequestDto);

//        List<OrderProductResponseDto> orderProduct =
//            orderAdaptor.findOrderProductList(uriComponents.toUri(), http);

//        model.addAttribute("orderProduct", orderProduct);

        // TODO: 2023/01/26 회원 배송지 정보를 위해 회원 정보를 보내줘야하나? and 회원 배송지 목록을 리스트로 보여주고 선택하게 해야 하나?

        return "mainpage/order/order-write";
    }
}
