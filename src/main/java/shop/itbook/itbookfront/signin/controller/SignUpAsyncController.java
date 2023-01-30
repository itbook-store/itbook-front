package shop.itbook.itbookfront.signin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookfront.signin.service.SignUpService;

/**
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/signup")
public class SignUpAsyncController {

    private final SignUpService signUpService;

    @GetMapping("/memberId/{memberId}")
    public MemberBooleanResponseDto memberIdExists(
        @PathVariable("memberId") String memberId) {

        return signUpService.checkMemberIdExists(memberId);
    }

    @GetMapping("/nickname/{nickname}")
    public MemberBooleanResponseDto nickNameExists(@PathVariable("nickname") String nickname) {

        return signUpService.checkNicknameExists(nickname);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public MemberBooleanResponseDto phoneNumberExists(@PathVariable("phoneNumber") String phoneNumber) {

        return signUpService.checkPhoneNumberExists(phoneNumber);
    }

    @GetMapping("/email/{email}")
    public MemberBooleanResponseDto emailExists(@PathVariable("email") String email) {

        return signUpService.checkEmailExists(email);
    }
}
