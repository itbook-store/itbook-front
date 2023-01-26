package shop.itbook.itbookfront.member.adaptor.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

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
        restTemplate.put(
            gatewayConfig.getGatewayServer() + "/api/service/members/" + memberId + "/info",
            memberUpdateRequestDto);
    }

    public MemberInfoResponseDto getMemberInfo(String memberId) {
        ResponseEntity<CommonResponseBody<MemberInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/service/members/" + memberId + "/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<CommonResponseBody<MemberInfoResponseDto>>() {
                });

        return responseEntity.getBody().getResult();
    }
}
