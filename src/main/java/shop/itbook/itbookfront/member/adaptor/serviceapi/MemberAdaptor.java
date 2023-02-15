package shop.itbook.itbookfront.member.adaptor.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.itbook.itbookfront.member.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberPointSendRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.GiftIncreaseDecreasePointHistoryNoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRecentlyPointResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public void modifyMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, Long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberUpdateRequestDto> httpEntity =
            new HttpEntity<>(memberUpdateRequestDto, headers);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/" + memberNo + "/info",
            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
            });

    }

    public MemberInfoResponseDto getMemberInfo(Long memberNo) {
        ResponseEntity<CommonResponseBody<MemberInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/" + memberNo,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public MemberInfoResponseDto getMemberByMemberId(String memberId) {
        ResponseEntity<CommonResponseBody<MemberInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/memberId/" + memberId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberStatusToWithdraw(Long memberNo,
                                             MemberStatusChangeRequestDto memberStatusChangeRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberStatusChangeRequestDto> httpEntity =
            new HttpEntity<>(memberStatusChangeRequestDto, headers);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/" + memberNo + "/withdraw",
            HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
            });

    }

    public List<MemberDestinationResponseDto> getMemberDestinationList(Long memberNo) {

        ResponseEntity<CommonResponseBody<List<MemberDestinationResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/" + memberNo +
                    "/member-destinations",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public void deleteMemberDestinations(
        List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<MemberDestinationNoResponseDto>> httpEntity =
            new HttpEntity<>(memberDestinationNoResponseDtoList, headers);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/memberDestinations/delete",
            HttpMethod.DELETE, httpEntity,
            new ParameterizedTypeReference<>() {
            });

    }

    public MemberDestinationNoResponseDto addMemberDestination(
        MemberDestinationRequestDto memberDestinationRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberDestinationRequestDto> httpEntity =
            new HttpEntity<>(memberDestinationRequestDto, headers);

        ResponseEntity<CommonResponseBody<MemberDestinationNoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/memberDestinations/add",
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberDestination(
        Long recipientDestinationNo,
        MemberDestinationRequestDto memberDestinationRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberDestinationRequestDto> httpEntity =
            new HttpEntity<>(memberDestinationRequestDto, headers);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/members/memberDestinations/" +
                recipientDestinationNo + "/modify", HttpMethod.PUT, httpEntity,
            new ParameterizedTypeReference<>() {
            });


    }

    public MemberDestinationResponseDto findMemberDestination(Long memberNo, Long recipientDestinationNo) {

        ResponseEntity<CommonResponseBody<MemberDestinationResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/"+ memberNo +"/memberDestinations/" +
                    recipientDestinationNo + "/info", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public MemberRecentlyPointResponseDto findMemberRecentlyPoint(Long memberNo) {

        ResponseEntity<CommonResponseBody<MemberRecentlyPointResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/" + memberNo + "/point/find",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public GiftIncreaseDecreasePointHistoryNoResponseDto giftPointMember(MemberPointSendRequestDto memberPointSendRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberPointSendRequestDto> httpEntity =
            new HttpEntity<>(memberPointSendRequestDto, headers);

        ResponseEntity<CommonResponseBody<GiftIncreaseDecreasePointHistoryNoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/" +
                    memberPointSendRequestDto.getSendMemberNo() + "/point-gift/send",
                HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }
}
