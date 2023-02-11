package shop.itbook.itbookfront.coupon.adaptor.adminapi.impl;

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
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponAdminAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private static final String BASE_API_URL = "/api/admin/coupons";
    private static final String ORDER_TOTAL_COUPON_API_URL = "/api/admin/order-total-coupons";
    private static final String CATEGORY_COUPON_API_URL = "/api/admin/category-coupons";
    private static final String PRODUCT_COUPON_API_URL = "/api/admin/product-coupons";

    public Long addCoupon(CouponInputRequestDto couponInputRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CouponInputRequestDto> httpEntity = new HttpEntity<>(couponInputRequestDto, headers);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + BASE_API_URL+"/add",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult().getCouponNo();
    }

    public Long addOrderTotalCoupon(OrderTotalCouponRequestDto couponRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderTotalCouponRequestDto> httpEntity = new HttpEntity<>(couponRequestDto, headers);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + ORDER_TOTAL_COUPON_API_URL+"/add",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult().getCouponNo();
    }

    public Long addCategoryCoupon(CategoryCouponRequestDto categoryCouponRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryCouponRequestDto> httpEntity = new HttpEntity<>(categoryCouponRequestDto, headers);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + CATEGORY_COUPON_API_URL+"/add",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult().getCouponNo();
    }

    public Long addProductCoupon(ProductCouponRequestDto productCouponRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductCouponRequestDto> httpEntity = new HttpEntity<>(productCouponRequestDto, headers);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + PRODUCT_COUPON_API_URL+"/add",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult().getCouponNo();
    }
    public PageResponse<AdminCouponListResponseDto> findCouponList(String couponListUrl){

        ResponseEntity<CommonResponseBody<PageResponse<AdminCouponListResponseDto>>> exchange =
        restTemplate.exchange(gatewayConfig.getGatewayServer() + couponListUrl,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
