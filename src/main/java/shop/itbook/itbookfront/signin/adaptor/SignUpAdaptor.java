package shop.itbook.itbookfront.signin.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class SignUpAdaptor {

    private final RestTemplate restTemplate;

    public MemberBooleanResponseDto memberIdExists(String memberId) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> restTemplateForObject = restTemplate.exchange(
            "http://localhost:8082/api/service/members/sign-up/memberId/" + memberId, HttpMethod.GET, null,
            new ParameterizedTypeReference<CommonResponseBody<MemberBooleanResponseDto>>() {
            });

        return getMemberBooleanResponseDto(restTemplateForObject);
    }

    public MemberBooleanResponseDto nicknameExists(String nickname) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> restTemplateForObject = restTemplate.exchange(
            "http://localhost:8082/api/service/members/sign-up/nickname/" + nickname,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<CommonResponseBody<MemberBooleanResponseDto>>() {
            });

        return getMemberBooleanResponseDto(restTemplateForObject);
    }

    public MemberBooleanResponseDto phoneNumberExists(String phoneNumber) {
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> restTemplateForObject = restTemplate.exchange(
            "http://localhost:8082/api/service/members/sign-up/phoneNumber/" + phoneNumber,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<CommonResponseBody<MemberBooleanResponseDto>>() {
            });

        return getMemberBooleanResponseDto(restTemplateForObject);
    }

    public MemberBooleanResponseDto emailExists(String email) {
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> restTemplateForObject = restTemplate.exchange(
            "http://localhost:8082/api/service/members/sign-up/email/" + email,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<CommonResponseBody<MemberBooleanResponseDto>>() {
            });

        return getMemberBooleanResponseDto(restTemplateForObject);
    }

    private MemberBooleanResponseDto getMemberBooleanResponseDto(
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> restTemplateForObject) {
        ResponseChecker.checkFail(restTemplateForObject.getBody().getHeader());

        return restTemplateForObject.getBody().getResult();
    }
}
