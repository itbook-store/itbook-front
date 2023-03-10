package shop.itbook.itbookfront.signin.service;

import shop.itbook.itbookfront.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookfront.signin.dto.request.MemberInputRequestDto;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface SignUpService {
    MemberBooleanResponseDto checkMemberIdExists(String memberId);

    MemberBooleanResponseDto checkNicknameExists(String nickname);

    MemberBooleanResponseDto checkPhoneNumberExists(String phoneNumber);

    MemberBooleanResponseDto checkEmailExists(String email);

    void addMember(MemberInputRequestDto memberInputRequestDto);

    void addSocialMember(MemberSocialRequestDto memberSocialRequestDto);
}
