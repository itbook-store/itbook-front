package shop.itbook.itbookfront.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.request.MemberSearchRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    PageResponse<MemberAdminResponseDto> findMembers(String url);

    PageResponse<MemberAdminResponseDto> findNormalMembers(String url);

    PageResponse<MemberAdminResponseDto> findBlockMembers(String url);

    PageResponse<MemberAdminResponseDto> findWithdrawMembers(String url);

    MemberAdminResponseDto findMember(Long memberNo);

    void updateMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto, Long memberNo);

    PageResponse<MemberAdminResponseDto> findMembersBySearch(String searchRequirement, String searchWord,
                                                     String memberStatusName, String url);

    PageResponse<MemberAdminResponseDto> findMemberByDateOfJoining(MemberSearchRequestDto memberSearchRequestDto, String memberStatusName, String url);

    MemberBlockInfoResponseDto findBlockMember(Long memberNo);

    List<MemberRoleResponseDto> findMemberRoles(Long memberNo);

    void addMemberRole(Long memberNo, String roleName);

    void deleteMemberRole(Long memberNo, Integer roleNo);

    MemberCountResponseDto countMemberByMemberStatus();

    MemberCountByMembershipResponseDto countMemberByMembership();
}
