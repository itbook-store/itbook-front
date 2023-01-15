package shop.itbook.itbookfront.signin.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.signin.adaptor.SignUpAdaptor;
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

    @Override
    public MemberBooleanResponseDto checkMemberIdExists(String memberId) {

        return signUpAdaptor.memberIdExists(memberId);
    }

    @Override
    public MemberBooleanResponseDto checkNicknameExists(String nickname) {
        System.out.println(signUpAdaptor.nicknameExists(nickname).getIsExists());

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
}
