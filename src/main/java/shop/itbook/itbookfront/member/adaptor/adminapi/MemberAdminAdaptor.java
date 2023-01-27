package shop.itbook.itbookfront.member.adaptor.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
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
import shop.itbook.itbookfront.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdminAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public List<MemberAdminResponseDto> getMembers() {
        ResponseEntity<CommonResponseBody<List<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
            );

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public MemberAdminResponseDto getMember(String memberId) {
        ResponseEntity<CommonResponseBody<MemberAdminResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberId, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                   String memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberStatusChangeRequestDto>
            httpEntity = new HttpEntity<>(memberStatusChangeRequestDto, headers);

        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberId, HttpMethod.PUT,
            httpEntity, new ParameterizedTypeReference<>() {
            }
        );

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

    }
}
