package shop.itbook.itbookfront.member.service.serviceapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.serviceapi.MemberAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberAdaptor memberAdaptor;

    @Override
    public void updateMemberInfo(String memberId, String name, String nickname, String password,
                                 String phoneNumber, String email) {
        MemberUpdateRequestDto memberUpdateRequestDto =
            MemberUpdateRequestDto.builder().name(name).nickname(nickname).password(password)
                .phoneNumber(phoneNumber).email(email).build();

        memberAdaptor.modifyMemberInfo(memberUpdateRequestDto, memberId);
    }

    @Override
    public MemberInfoResponseDto findMemberInfo(String memberId) {
        return memberAdaptor.getMemberInfo(memberId);
    }

    @Override
    public void withdrawMember(String memberId, MemberStatusChangeRequestDto requestDto) {
        memberAdaptor.modifyMemberStatusToWithDraw(memberId, requestDto);
    }
}
