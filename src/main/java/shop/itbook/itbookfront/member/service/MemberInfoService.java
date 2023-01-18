package shop.itbook.itbookfront.member.service;

import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface MemberInfoService {
    void updateMemberInfo(MemberUpdateRequestDto memberUpdateRequestDto, String memberId);
}
