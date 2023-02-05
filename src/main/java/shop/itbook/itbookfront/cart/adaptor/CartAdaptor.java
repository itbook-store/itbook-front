package shop.itbook.itbookfront.cart.adaptor;

import java.util.List;
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
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
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


    public boolean addProductInCart(CartMemberRequestDto cartMemberRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + BASE_API_URL,
                HttpMethod.POST,
                new HttpEntity<>(cartMemberRequestDto, headers),
                new ParameterizedTypeReference<>() {
                }
            );

        SuccessfulResponseDto result = Objects.requireNonNull(exchange.getBody()).getResult();

        return result.getIsSuccessful();
    }

    public List<ProductDetailsResponseDto> getProductListAnonymous(String productNoList) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> exchange = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + PRODUCT_LIST_ANONYMOUS_API + productNoList +"?page=0&size=100",
            HttpMethod.GET,
            new HttpEntity<>(productNoList, headers),
            new ParameterizedTypeReference<>() {
            }
        );

        Objects.requireNonNull(exchange.getBody());

        List<ProductDetailsResponseDto> content = Objects.requireNonNull(exchange.getBody().getResult()).getContent();
        content.stream().forEach(dto -> dto.setSelledPrice(
            (long) (dto.getFixedPrice() * ((100 - dto.getDiscountPercent()) * 0.01))));

        return Objects.requireNonNull(exchange.getBody()).getResult().getContent();
    }

    public List<CartProductDetailsResponseDto> getProductListMember(CartMemberNoRequestDto cartMemberNoRequestDto) {

        ResponseEntity<CommonResponseBody<List<CartProductDetailsResponseDto>>> exchange = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + BASE_API_URL +"/" + cartMemberNoRequestDto.getMemberNo(),
            HttpMethod.GET,
            new HttpEntity<>(cartMemberNoRequestDto),
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

    public void deleteProductInCart(CartMemberRequestDto cartMemberRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + BASE_API_URL,
            HttpMethod.DELETE,
            new HttpEntity<>(cartMemberRequestDto, headers),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public void deleteAllProductInCart(CartMemberNoRequestDto cartMemberNoRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + BASE_API_URL + "/" + cartMemberNoRequestDto.getMemberNo(),
            HttpMethod.DELETE,
            new HttpEntity<>(cartMemberNoRequestDto, headers),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public void modifyProductCountInCart(CartMemberRequestDto cartMemberRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + BASE_API_URL,
            HttpMethod.PUT,
            new HttpEntity<>(cartMemberRequestDto, headers),
            new ParameterizedTypeReference<>() {
            }
        );
    }
}
