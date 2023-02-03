package shop.itbook.itbookfront.membership.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.membership.adaptor.MembershipAdaptor;
import shop.itbook.itbookfront.membership.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookfront.membership.service.MembershipService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipAdaptor membershipAdaptor;

    @Override
    public List<MembershipResponseDto> getMemberships() {
        return membershipAdaptor.getMemberships();
    }

    @Override
    public List<MembershipHistoryResponseDto> getMembershipHistories(String memberId) {
        return membershipAdaptor.getMembershipHistoriesByMemberId(memberId);
    }
}
