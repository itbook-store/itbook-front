package shop.itbook.itbookfront.member.adaptor.adminapi;

import java.util.List;
import java.util.Objects;
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
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.member.dto.request.MemberSearchRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
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

    public PageResponse<MemberAdminResponseDto> getWriterMembers(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/writer/list" + url, HttpMethod.GET,
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
                gatewayConfig.getGatewayServer() + "/api/admin/members/normal" + url,
                HttpMethod.GET,
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
                gatewayConfig.getGatewayServer() + "/api/admin/members/withdraw" + url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public MemberAdminResponseDto getMember(Long memberNo) {

        ResponseEntity<CommonResponseBody<MemberAdminResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberNo, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                   Long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberStatusChangeRequestDto>
            httpEntity = new HttpEntity<>(memberStatusChangeRequestDto, headers);

        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberNo, HttpMethod.PUT,
            httpEntity, new ParameterizedTypeReference<>() {
            }
        );

    }

    public PageResponse<MemberAdminResponseDto> getMembersBySearch(String searchRequirement,
                                                                   String searchWord,
                                                                   String memberStatusName,
                                                                   String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/search/" + memberStatusName +
                    "/" +
                    searchRequirement + "/" + searchWord + url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public PageResponse<MemberAdminResponseDto> getMembersByDateOfJoining(MemberSearchRequestDto memberSearchRequestDto,
                                                                          String memberStatusName,
                                                                          String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MemberSearchRequestDto> httpEntity = new HttpEntity<>(memberSearchRequestDto, headers);

        ResponseEntity<CommonResponseBody<PageResponse<MemberAdminResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/search/" + memberStatusName +
                    url, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }

    public MemberBlockInfoResponseDto getBlockMember(Long memberNo) {

        ResponseEntity<CommonResponseBody<MemberBlockInfoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/members/" + memberNo + "/block",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
            );

        return responseEntity.getBody().getResult();
    }

    public List<MemberRoleResponseDto> getMemberRoles(Long memberNo) {

        ResponseEntity<CommonResponseBody<List<MemberRoleResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/member-roles/" + memberNo, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        return responseEntity.getBody().getResult();
    }

    public void addMemberRole(Long memberNo, String roleName) {

        ResponseEntity<CommonResponseBody<Void>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/member-roles/" + memberNo + "/" +
                    roleName + "/add",
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                });

    }

    public void deleteMemberRole(Long memberNo, Integer roleNo) {

        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/member-roles/" + memberNo + "/" + roleNo +
                "/delete", HttpMethod.DELETE, null,
            new ParameterizedTypeReference<>() {
            });

    }

    public MemberCountResponseDto countMember() {

        ResponseEntity<CommonResponseBody<MemberCountResponseDto>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members/memberStatus/count",
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });


        return responseEntity.getBody().getResult();
    }

    public MemberCountByMembershipResponseDto countMemberByMembership() {

        ResponseEntity<CommonResponseBody<MemberCountByMembershipResponseDto>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members/membership/count",
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        return responseEntity.getBody().getResult();
    }

    public MemberBooleanResponseDto nameExists(String memberId, String name) {
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/members/register-check/memberId/" + memberId +"/name/" + name,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return getMemberBooleanResponseDto(responseEntity);
    }

    public MemberBooleanResponseDto getMemberBooleanResponseDto(
        ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> responseEntity) {

        return responseEntity.getBody().getResult();
    }

    public void modifyMemberWriter(Long memberNo) {
        ResponseEntity<CommonResponseBody<Void>> responseEntity = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/members/modify/writer/" + memberNo, HttpMethod.PUT,
            null, new ParameterizedTypeReference<>() {
            }
        );

    }
}
