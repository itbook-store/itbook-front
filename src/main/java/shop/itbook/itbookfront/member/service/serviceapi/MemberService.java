package shop.itbook.itbookfront.member.service.serviceapi;

import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberService {

    void updateMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId);

    MemberInfoResponseDto findMemberInfo(String memberId);

    void withdrawMember(String memberId, MemberStatusChangeRequestDto requestDto);

}
