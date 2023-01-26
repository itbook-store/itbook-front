package shop.itbook.itbookfront.member.adaptor.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdminAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public List<MemberInfoResponseDto> getMembers() {
        ResponseEntity<CommonResponseBody<List<MemberInfoResponseDto>>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members", HttpMethod.GET, null,
            new ParameterizedTypeReference<CommonResponseBody<List<MemberInfoResponseDto>>>() {
            }
        );

        return responseEntity.getBody().getResult();
    }
}
