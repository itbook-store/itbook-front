package shop.itbook.itbookfront.delivery.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
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

    private final String DELIVERY_LIST_PATH = "/api/admin/deliveries";
    private final String DELIVERY_WAIT_LIST_PATH = "/api/admin/deliveries/wait";
    private final String DELIVERY_LIST_POST_PATH = "/api/admin/deliveries/post";

    @Override
    public void postDeliveryList(RedirectAttributes redirectAttributes) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
            gatewayConfig.getGatewayServer()).path(DELIVERY_LIST_POST_PATH).build();

        try {
            deliveryAdaptor.postDeliveryList(uriComponents.toUri());
        } catch (DeliveryNoWaitStatusException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
    }

    @Override
    public List<DeliveryWithStatusResponseDto> getDeliveryList() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
            gatewayConfig.getGatewayServer()).path(DELIVERY_LIST_PATH).build();

        return Objects.requireNonNull(
            deliveryAdaptor.getDeliveryWaitList(uriComponents.toUri()).getBody()).getResult();
    }

    @Override
    public List<DeliveryWithStatusResponseDto> getDeliveryWaitList() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
            gatewayConfig.getGatewayServer()).path(DELIVERY_WAIT_LIST_PATH).build();

        return Objects.requireNonNull(
            deliveryAdaptor.getDeliveryWaitList(uriComponents.toUri()).getBody()).getResult();
    }
}
