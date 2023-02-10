package shop.itbook.itbookfront.signin.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookfront.signin.adaptor.SignUpAdaptor;
import shop.itbook.itbookfront.signin.dto.request.MemberInputRequestDto;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookfront.signin.service.SignUpService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final SignUpAdaptor signUpAdaptor;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberBooleanResponseDto checkMemberIdExists(String memberId) {

        return signUpAdaptor.memberIdExists(memberId);
    }

    @Override
    public MemberBooleanResponseDto checkNicknameExists(String nickname) {

        return signUpAdaptor.nicknameExists(nickname);
    }

    @Override
    public MemberBooleanResponseDto checkPhoneNumberExists(String phoneNumber) {

        return signUpAdaptor.phoneNumberExists(phoneNumber);
    }

    @Override
    public MemberBooleanResponseDto checkEmailExists(String email) {

        return signUpAdaptor.emailExists(email);
    }

    @Override
    public void addMember(MemberInputRequestDto memberInputRequestDto) {

        MemberRequestDto memberRequestDto =
            MemberRequestDto.builder().membershipName("일반").memberStatusName("정상회원").memberId(
                    memberInputRequestDto.getMemberId()).nickname(memberInputRequestDto.getNickname())
                .name(
                    memberInputRequestDto.getName()).isMan(memberInputRequestDto.getIsMan())
                .birth(LocalDate.parse(memberInputRequestDto.getBirth(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                .password(passwordEncoder.encode(memberInputRequestDto.getPassword())).phoneNumber(
                    memberInputRequestDto.getPhoneNumber()).email(memberInputRequestDto.getEmail())
                .isSocial(memberInputRequestDto.getIsSocial()).build();

        signUpAdaptor.addMemberIntoDb(memberRequestDto);
    }

    @Override
    public void addSocialMember(MemberSocialRequestDto memberSocialRequestDto) {
        StringBuffer sb = new StringBuffer();
        sb.append(memberSocialRequestDto.getPhoneNumber());
        sb.insert(3, "-");
        sb.insert(8, "-");

        memberSocialRequestDto.setPhoneNumber(sb.toString());

        signUpAdaptor.addSocialMember(memberSocialRequestDto);
    }
}
