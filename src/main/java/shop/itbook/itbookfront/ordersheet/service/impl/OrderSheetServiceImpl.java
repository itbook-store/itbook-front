package shop.itbook.itbookfront.ordersheet.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.response.OrderSheetDetailResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookfront.ordersheet.service.OrderSheetService;
import shop.itbook.itbookfront.ordersheet.util.OrderSheetUtil;

/**
 * OrderSheetService 인터페이스의 구현 클래스
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderSheetServiceImpl implements OrderSheetService {

    private final GatewayConfig gatewayConfig;
    private final OrderAdaptor orderAdaptor;

    private static final String ORDER_SHEET_PATH = "/api/order-sheet";

    @Override
    public OrderSheetDetailResponseDto findOrderSheetOneProduct(Long productNo, Integer productCnt,
                                                                Long memberNo) {
        UriComponents uriComponents =
            UriComponentsBuilder.fromUriString(gatewayConfig.getGatewayServer())
                .path(ORDER_SHEET_PATH)
                .queryParam("productNo", productNo)
                .queryParam("memberNo", memberNo)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        OrderSheetResponseDto orderSheet =
            orderAdaptor.findOrderSheet(uriComponents.toUri(), httpEntity);


        return OrderSheetUtil.convertOrderSheetDtoOnProduct(orderSheet, productCnt);
    }
}
