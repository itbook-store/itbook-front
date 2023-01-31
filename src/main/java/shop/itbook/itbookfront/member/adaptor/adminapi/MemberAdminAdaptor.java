package shop.itbook.itbookfront.member.adaptor.adminapi;

import java.util.List;
import java.util.Objects;
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
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
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

    public PageResponse<MemberAdminResponseDto> getMembers(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members" + url, HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public PageResponse<MemberAdminResponseDto> getNormalMembers(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/normal" + url, HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public PageResponse<MemberAdminResponseDto> getBlockMembers(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/block" + url, HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public PageResponse<MemberAdminResponseDto> getWithdrawMembers(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/withdraw" + url, HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
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

    public List<MemberAdminResponseDto> getMembersBySearch(String searchRequirement,
                                                           String searchWord) {
        ResponseEntity<CommonResponseBody<List<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/search/" +
                    searchRequirement + "/" + searchWord, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
            );

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public MemberBlockInfoResponseDto getBlockMember(String memberId) {
        ResponseEntity<CommonResponseBody<MemberBlockInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberId + "/block",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
            );

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

}
