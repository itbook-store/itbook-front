package shop.itbook.itbookfront.cart.adaptor;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.cart.dto.response.CartResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartProductNoRequestDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;

/**
 * @author 강명관
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CartAdaptor {

    private final RestTemplate restTemplate;

    private final GatewayConfig gatewayConfig;

    private static final String ADD_CART_API = "";

    private static final String PRODUCT_LIST_ANONYMOUS_API = "";

    private static final String PRODUCT_LIST_MEMBER_API = "";

    private static final String DELETE_PRODUCT_API = "";

    private static final String DELETE_ALL_PRODUCT_API = "";

    public boolean addProductInCart(CartMemberRequestDto cartMemberRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + ADD_CART_API,
                HttpMethod.POST,
                new HttpEntity<>(cartMemberRequestDto, headers),
                new ParameterizedTypeReference<>() {
                }
            );

        SuccessfulResponseDto result = Objects.requireNonNull(exchange.getBody()).getResult();

        return result.getIsSuccessful();
    }

    public List<CartResponseDto> getProductListAnonymous(List<CartProductNoRequestDto> productNoList) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<List<CartResponseDto>>> exchange = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + PRODUCT_LIST_ANONYMOUS_API,
            HttpMethod.GET,
            new HttpEntity<>(productNoList, headers),
            new ParameterizedTypeReference<>() {
            }
        );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public List<CartResponseDto> getProductListMember(CartMemberNoRequestDto cartMemberNoRequestDto) {

        ResponseEntity<CommonResponseBody<List<CartResponseDto>>> exchange = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + PRODUCT_LIST_MEMBER_API,
            HttpMethod.GET,
            new HttpEntity<>(cartMemberNoRequestDto),
            new ParameterizedTypeReference<>() {
            }
        );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void deleteProductInCart(CartMemberRequestDto cartMemberRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + DELETE_PRODUCT_API,
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
            gatewayConfig.getGatewayServer() + DELETE_ALL_PRODUCT_API,
            HttpMethod.DELETE,
            new HttpEntity<>(cartMemberNoRequestDto, headers),
            new ParameterizedTypeReference<>() {
            }
        );
    }
}
