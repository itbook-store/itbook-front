package shop.itbook.itbookfront.cart.adaptor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.dto.response.CartResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * 장바구니 기능에 대해 Back 서버와 통신하는 로직을 담당하는 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CartAdaptor {

    private final RestTemplate restTemplate;

    private final GatewayConfig gatewayConfig;

    private static final String BASE_API_URL = "/api/cart";

    private static final String PRODUCT_LIST_ANONYMOUS_API = "/api/products/";


    public List<CartProductDetailsResponseDto> getProductListMember(Long memberNo) {

        ResponseEntity<CommonResponseBody<List<CartProductDetailsResponseDto>>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + BASE_API_URL + "/" + memberNo,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        List<CartProductDetailsResponseDto> result =
            Objects.requireNonNull(exchange.getBody()).getResult();

        result.forEach(dto -> dto.getProductDetailsResponseDto().setSelledPrice(
            (long) (dto.getProductDetailsResponseDto().getFixedPrice() *
                ((100 - dto.getProductDetailsResponseDto().getDiscountPercent()) * 0.01))
        ));

        return result;
    }

    public CartProductDetailsResponseDto addCartProduct(Integer productNo) {

        ResponseEntity<CommonResponseBody<ProductDetailsResponseDto>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/products/" + productNo,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        ProductDetailsResponseDto result = Objects.requireNonNull(exchange.getBody()).getResult();

        return new CartProductDetailsResponseDto(1, result);

    }

    public void saveAllCart(List<CartMemberRequestDto> redisHashMapList) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + BASE_API_URL + "/save-all",
                HttpMethod.POST,
                new HttpEntity<>(redisHashMapList, headers),
                new ParameterizedTypeReference<>() {
                }
            );
        } catch (BadRequestException e) {
            log.error("BadRequestException {}", e.getMessage());
        } catch (RestApiServerException e) {
            log.error("RestApiServerException {}", e.getMessage());
        }


    }


}
