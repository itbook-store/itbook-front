package shop.itbook.itbookfront.signin.service.impl;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
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

        MemberRequestDto memberRequestDto = new MemberRequestDto(428, 392,
            memberInputRequestDto.getMemberId(), memberInputRequestDto.getNickname(),
            memberInputRequestDto.getName(), memberInputRequestDto.getIsMan(),
            LocalDate.parse(memberInputRequestDto.getBirth(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
            passwordEncoder.encode(memberInputRequestDto.getPassword()),
            memberInputRequestDto.getPhoneNumber(), memberInputRequestDto.getEmail());

        signUpAdaptor.addMemberIntoDb(memberRequestDto);
    }
}
