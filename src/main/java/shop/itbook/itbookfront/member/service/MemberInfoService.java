package shop.itbook.itbookfront.member.service;

import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberInfoService {
    void updateMemberInfo(String memberId, String name, String nickname, String password, String phoneNumber, String email);

    MemberInfoResponseDto findMemberInfo(String memberId);

}
