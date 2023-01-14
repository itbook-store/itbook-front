package shop.itbook.itbookfront.order.serviceapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Front 서버에서 관리자의 주문 관련 요청을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
public class OrderController {

    @GetMapping("/api/order")
    public String myOrderDeliveryPage() {
        return "mypage/order/my-order-list";
    }
}
