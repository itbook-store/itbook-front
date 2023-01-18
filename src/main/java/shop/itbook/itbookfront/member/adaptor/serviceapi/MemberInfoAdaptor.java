package shop.itbook.itbookfront.member.adaptor.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberInfoAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public void modifyMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId) {
        restTemplate.put(
            gatewayConfig.getGatewayServer() + "/api/service/members/" + memberId + "/info",
            memberUpdateRequestDto);
    }
}
