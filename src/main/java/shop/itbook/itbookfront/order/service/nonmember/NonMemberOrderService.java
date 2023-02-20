package shop.itbook.itbookfront.order.service.nonmember;

import java.util.List;
import shop.itbook.itbookfront.order.dto.request.NonMemberOrderDetailsSearchDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionDetailsResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface NonMemberOrderService {
    OrderDetailsResponseDto findNonMemberOrderDetails(
        NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto);

    List<OrderSubscriptionDetailsResponseDto> findNonMemberSubscriptionOrderDetails(
        NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto);
}
