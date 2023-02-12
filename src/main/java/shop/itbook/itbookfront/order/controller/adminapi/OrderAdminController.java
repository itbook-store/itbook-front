package shop.itbook.itbookfront.order.controller.adminapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front 서버에서 관리자의 주문 관련 요청을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

    @GetMapping
    public String adminOrderListPage() {
        return "adminpage/order/admin-order-list";
    }
}