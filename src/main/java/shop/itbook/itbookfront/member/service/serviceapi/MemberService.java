package shop.itbook.itbookfront.member.service.serviceapi;

import java.util.List;
import shop.itbook.itbookfront.member.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberPointSendRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRecentlyPointResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberService {

    void updateMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, Long memberNo);

    MemberInfoResponseDto findMember(Long memberNo);

    MemberInfoResponseDto findMemberByMemberId(String memberId);

    void withdrawMember(Long memberNo, MemberStatusChangeRequestDto requestDto);

    List<MemberDestinationResponseDto> findMemberDestinationList(Long memberNo);

    void deleteMemberDestinations(List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList);

    Long addMemberDestination(MemberDestinationRequestDto memberDestinationRequestDto);

    void modifyMemberDestination(Long recipientDestinationNo, MemberDestinationRequestDto memberDestinationRequestDto);

    MemberDestinationResponseDto findMemberDestinationDetails(Long recipientDestinationNo);

    MemberRecentlyPointResponseDto findMemberRecentlyPoint(Long memberNo);

    Long giftPointMember(MemberPointSendRequestDto memberPointSendRequestDto);
}
