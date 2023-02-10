package shop.itbook.itbookfront.membership.service;

import java.util.List;
import shop.itbook.itbookfront.membership.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MembershipService {

    List<MembershipResponseDto> getMemberships();

    List<MembershipHistoryResponseDto> getMembershipHistories(Long memberNo);
}
