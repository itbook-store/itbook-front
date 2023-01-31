package shop.itbook.itbookfront.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    PageResponse<MemberAdminResponseDto> findMembers(String url);

    PageResponse<MemberAdminResponseDto> findNormalMembers(String url);

    PageResponse<MemberAdminResponseDto> findBlockMembers(String url);

    PageResponse<MemberAdminResponseDto> findWithdrawMembers(String url);

    MemberAdminResponseDto findMember(String memberId);

    void updateMemberStatus(MemberStatusChangeRequestDto memberStatusChangeRequestDto, String memberId);

    List<MemberAdminResponseDto> findMembersBySearch(String searchRequirement, String searchWord);

    MemberBlockInfoResponseDto findBlockMember(String memberId);
}
