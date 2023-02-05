package shop.itbook.itbookfront.order.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 주문 완료 처리를 위한 컨트롤러.
     * 로직을 다 처리한 후 완료 페이지를 반환한다.
     *
     * @return 사용자에게 보여줄 주문 완료페이지
     */
    @GetMapping("/completion")
    public String orderCompletion() {
        return "mainpage/order/orderCompletionForm";
    }
}
