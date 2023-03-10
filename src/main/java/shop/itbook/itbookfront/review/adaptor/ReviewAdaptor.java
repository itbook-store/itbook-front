package shop.itbook.itbookfront.review.adaptor;

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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookfront.review.dto.response.ReviewNoResponseDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookfront.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookfront.review.exception.ReviewAlreadyRegisteredException;
import shop.itbook.itbookfront.review.exception.ReviewNotFoundException;
import shop.itbook.itbookfront.signin.dto.response.MemberNoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * 리뷰 어댑터 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<ReviewResponseDto> getReviewListByMemberNo(String url,
                                                                   Long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<CommonResponseBody<PageResponse<ReviewResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/reviews/list/" + memberNo + url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("images", images.getResource());
        params.add("reviewRequestDto", reviewRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<ReviewNoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/reviews/add", HttpMethod.POST,
                uploadEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponseBody<ReviewNoResponseDto> body = responseEntity.getBody();

        return body.getResult().getOrderProductNo();
    }

    public ReviewResponseDto getReview(Long orderProductNo) {

        ResponseEntity<CommonResponseBody<ReviewResponseDto>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/reviews/" + orderProductNo,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public ReviewResponseDto getReviewForModify(Long memberNo, Long orderProductNo) {
        ResponseEntity<CommonResponseBody<ReviewResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/reviews/modify-form/" + memberNo + "/" + orderProductNo,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public void deleteReview(Long orderProductNo) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/reviews/" + orderProductNo + "/delete",
            HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
            });
    }

    public void modifyReview(Long orderProductNo,
                             ReviewRequestDto reviewRequestDto,
                             MultipartFile images) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("images", images.getResource());
        params.add("reviewRequestDto", reviewRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/reviews/" + orderProductNo + "/modify",
            HttpMethod.PUT,
            uploadEntity,
            new ParameterizedTypeReference<>() {
            });

    }

    public PageResponse<ReviewResponseDto> getReviewListByProductNo(String url,
                                                                    Long productNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<CommonResponseBody<PageResponse<ReviewResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/reviews/list/product/" + productNo + url,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public PageResponse<UnwrittenReviewOrderProductResponseDto> getUnwrittenReviewOrderProductList(
        String url, Long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<CommonResponseBody<PageResponse<UnwrittenReviewOrderProductResponseDto>>>
            responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/reviews/unwritten-list/member/" +
                    memberNo + url,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }
}
