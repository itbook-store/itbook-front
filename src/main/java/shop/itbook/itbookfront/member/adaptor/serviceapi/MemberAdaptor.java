package shop.itbook.itbookfront.member.adaptor.serviceapi;

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
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public void modifyMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberUpdateRequestDto> httpEntity = new HttpEntity<>(memberUpdateRequestDto, headers);

        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/" + memberId + "/info",
            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

    }

    public MemberInfoResponseDto getMemberInfo(String memberId) {
        ResponseEntity<CommonResponseBody<MemberInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/" + memberId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberStatusToWithDraw(String memberId, MemberStatusChangeRequestDto memberStatusChangeRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberStatusChangeRequestDto> httpEntity = new HttpEntity<>(memberStatusChangeRequestDto, headers);

        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/" + memberId + "/withdraw",
            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());
    }
}