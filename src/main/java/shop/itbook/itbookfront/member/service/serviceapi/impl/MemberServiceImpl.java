package shop.itbook.itbookfront.member.service.serviceapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.adaptor.serviceapi.MemberAdaptor;
import shop.itbook.itbookfront.member.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRecentlyPointResponseDto;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberAdaptor memberAdaptor;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId) {

        if (!memberUpdateRequestDto.getPassword().contains("$2a$10$")) {
            memberUpdateRequestDto.setPassword(passwordEncoder.encode(memberUpdateRequestDto.getPassword()));
        }

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

    @Override
    public List<MemberDestinationResponseDto> findMemberDestinationList(Long memberNo) {
        return memberAdaptor.getMemberDestinationList(memberNo);
    }

    @Override
    public void deleteMemberDestinations(
        List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList) {

        memberAdaptor.deleteMemberDestinations(memberDestinationNoResponseDtoList);
    }

    @Override
    public Long addMemberDestination(MemberDestinationRequestDto memberDestinationRequestDto) {

        StringBuffer sb = new StringBuffer();
        sb.append(memberDestinationRequestDto.getRecipientPhoneNumber());
        sb.insert(3, "-");
        sb.insert(8, "-");

        memberDestinationRequestDto.setRecipientPhoneNumber(sb.toString());

        return memberAdaptor.addMemberDestination(memberDestinationRequestDto).getRecipientDestinationNo();
    }

    @Override
    public void modifyMemberDestination(Long recipientDestinationNo,
                                        MemberDestinationRequestDto memberDestinationRequestDto) {

        StringBuffer sb = new StringBuffer();
        sb.append(memberDestinationRequestDto.getRecipientPhoneNumber());
        sb.insert(3, "-");
        sb.insert(8, "-");

        memberDestinationRequestDto.setRecipientPhoneNumber(sb.toString());

        memberAdaptor.modifyMemberDestination(recipientDestinationNo, memberDestinationRequestDto);
    }

    @Override
    public MemberDestinationResponseDto findMemberDestinationDetails(Long recipientDestinationNo) {

        return memberAdaptor.findMemberDestination(recipientDestinationNo);
    }

    @Override
    public MemberRecentlyPointResponseDto findMemberRecentlyPoint(Long memberNo) {

        return memberAdaptor.findMemberRecentlyPoint(memberNo);
    }
}
