package shop.itbook.itbookfront.order.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.order.service.OrderService;

/**
 * Front 서버에서 관리자의 주문 관련 요청을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class OrderAdminController {

    private final OrderService orderService;

    @GetMapping("/list")
    public String adminOrderListPage(@PageableDefault Pageable pageable, Model model) {

        model.addAttribute("pageResponse", orderService.findOrderListAll(pageable));

        return "adminpage/order/admin-order-list";
    }

    @GetMapping("/list/subscription")
    public String adminSubscriptionOrderListPage(@PageableDefault Pageable pageable, Model model) {

        model.addAttribute("pageResponse", orderService.orderSubscriptionListByAdmin(pageable));

        return "adminpage/order/order-subscription-list";
    }
}
