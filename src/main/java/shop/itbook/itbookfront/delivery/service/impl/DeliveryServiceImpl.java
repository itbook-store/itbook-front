package shop.itbook.itbookfront.delivery.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.delivery.adminapi.adaptor.DeliveryAdaptor;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;
import shop.itbook.itbookfront.delivery.adminapi.exception.DeliveryNoWaitStatusException;
import shop.itbook.itbookfront.delivery.service.DeliveryService;

/**
 * DeliveryService 인터페이스의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final GatewayConfig gatewayConfig;
    private final DeliveryAdaptor deliveryAdaptor;

    @Override
    public void postDeliveryList(RedirectAttributes redirectAttributes) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/admin/deliveries/registration")
            .build();

        try {
            deliveryAdaptor.postDeliveryList(uriComponents.toUri());
        } catch (DeliveryNoWaitStatusException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
    }

    @Override
    public PageResponse<DeliveryWithStatusResponseDto> findDeliveryList(Pageable pageable) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/admin/deliveries")
            .queryParam(String.format("page=%d&size=%d", pageable.getPageNumber(),
                pageable.getPageSize()))
            .build();

        return Objects.requireNonNull(
            deliveryAdaptor.findDeliveryWaitList(uriComponents.toUri()).getBody()).getResult();
    }

    @Override
    public PageResponse<DeliveryWithStatusResponseDto> findDeliveryWaitList(Pageable pageable) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/admin/deliveries/wait-list")
            .queryParam(String.format("page=%d&size=%d", pageable.getPageNumber(),
                pageable.getPageSize()))
            .build();

        return Objects.requireNonNull(
            deliveryAdaptor.findDeliveryWaitList(uriComponents.toUri()).getBody()).getResult();
    }
}
