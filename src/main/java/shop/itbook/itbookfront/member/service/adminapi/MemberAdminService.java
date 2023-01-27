package shop.itbook.itbookfront.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    List<MemberAdminResponseDto> findMembers();

    MemberAdminResponseDto findMember(String memberId);

    void updateMemberStatus(String memberStatusName, String memberId);
}
