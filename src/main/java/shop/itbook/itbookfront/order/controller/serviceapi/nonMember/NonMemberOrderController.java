package shop.itbook.itbookfront.order.controller.serviceapi.nonMember;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.order.dto.request.NonMemberOrderDetailsSearchDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookfront.order.service.nonmember.NonMemberOrderService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/non-member")
@RequiredArgsConstructor
public class NonMemberOrderController {

    private final NonMemberOrderService nonMemberOrderService;

    @PostMapping("/order-details")
    public String findNonMemberOrderDetails(
        @Valid @ModelAttribute NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto,
        Model model) {

        OrderDetailsResponseDto orderDetails =
            nonMemberOrderService.findNonMemberOrderDetails(nonMemberOrderDetailsSearchDto);

        model.addAttribute("orderDetails", orderDetails);

        return "mainpage/order/mainOrderDetailsForm";
    }

    @PostMapping("/subscription-order-details")
    public String findSubscriptionOrderDetails(
        @Valid @ModelAttribute NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto,
        Model model) {

        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsList =
            nonMemberOrderService.findNonMemberSubscriptionOrderDetails(
                nonMemberOrderDetailsSearchDto);

        model.addAttribute("detailsList", orderSubscriptionDetailsList);
        return "mainpage/order/mainOrderSubDetailsForm";
    }
}
