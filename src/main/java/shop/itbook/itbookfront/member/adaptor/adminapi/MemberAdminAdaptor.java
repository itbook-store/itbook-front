package shop.itbook.itbookfront.member.adaptor.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateAdminRequestDto;
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
                new ParameterizedTypeReference<CommonResponseBody<List<MemberAdminResponseDto>>>() {
                }
            );

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

        ResponseChecker.checkFail(responseEntity.getStatusCode(), responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberStatus(MemberUpdateAdminRequestDto memberUpdateAdminRequestDto,
                                   String memberId) {

        restTemplate.put(
            gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberId,
            memberUpdateAdminRequestDto
        );


    }
}
