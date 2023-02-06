package shop.itbook.itbookfront.delivery.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;

/**
 * 배송 관련 로직을 담당하는 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryService {

    void postDeliveryList(RedirectAttributes redirectAttributes);

    PageResponse<DeliveryWithStatusResponseDto> findDeliveryList(Pageable pageable);

    PageResponse<DeliveryWithStatusResponseDto> findDeliveryWaitList(Pageable pageable);

}
