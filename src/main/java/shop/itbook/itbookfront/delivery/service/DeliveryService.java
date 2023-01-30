package shop.itbook.itbookfront.delivery.service;

import java.net.URI;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;

/**
 * 배송 관련 로직을 담당하는 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryService {

    void postDeliveryList(RedirectAttributes redirectAttributes);

    List<DeliveryWithStatusResponseDto> getDeliveryList();

    List<DeliveryWithStatusResponseDto> getDeliveryWaitList();

}
