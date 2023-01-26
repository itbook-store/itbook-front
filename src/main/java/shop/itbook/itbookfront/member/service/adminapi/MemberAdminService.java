package shop.itbook.itbookfront.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    List<MemberInfoResponseDto> findMembers();
}
