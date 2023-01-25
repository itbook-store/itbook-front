package shop.itbook.itbookfront.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.serviceapi.MemberInfoAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
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
    public void updateMemberInfo(String memberId, String name, String nickname, String password,
                                 String phoneNumber, String email) {
        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto(name, nickname, password, phoneNumber, email);

        memberInfoAdaptor.modifyMemberInfo(memberUpdateRequestDto, memberId);
    }

    @Override
    public MemberInfoResponseDto findMemberInfo(String memberId) {
        return memberInfoAdaptor.getMemberInfo(memberId);
    }
}
