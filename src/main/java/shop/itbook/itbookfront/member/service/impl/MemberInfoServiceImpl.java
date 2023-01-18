package shop.itbook.itbookfront.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.serviceapi.MemberInfoAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.service.MemberInfoService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberInfoAdaptor memberInfoAdaptor;

    @Override
    public void updateMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId) {
        memberInfoAdaptor.modifyMemberInfo(memberUpdateRequestDto, memberId);
    }
}
