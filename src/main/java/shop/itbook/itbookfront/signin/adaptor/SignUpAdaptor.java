package shop.itbook.itbookfront.signin.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookfront.signin.dto.response.MemberNoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class SignUpAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public MemberBooleanResponseDto memberIdExists(String memberId) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/service/members/sign-up/memberId/" +
                    memberId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return getMemberBooleanResponseDto(responseEntity);
    }

    public MemberBooleanResponseDto nicknameExists(String nickname) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/service/members/sign-up/nickname/" +
                    nickname,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return getMemberBooleanResponseDto(responseEntity);
    }

    public MemberBooleanResponseDto phoneNumberExists(String phoneNumber) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/service/members/sign-up/phoneNumber/" +
                    phoneNumber,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return getMemberBooleanResponseDto(responseEntity);
    }

    public MemberBooleanResponseDto emailExists(String email) {

        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/service/members/sign-up/email/" + email,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return getMemberBooleanResponseDto(responseEntity);
    }

    public MemberBooleanResponseDto getMemberBooleanResponseDto(
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity) {

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public MemberNoResponseDto addMemberIntoDb(
        MemberRequestDto memberRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberRequestDto>
            httpEntity = new HttpEntity<>(memberRequestDto, headers);

        ResponseEntity<CommonResponseBody<MemberNoResponseDto>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/service/members/sign-up", HttpMethod.POST,
            httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }
}
