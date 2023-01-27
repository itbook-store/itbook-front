package shop.itbook.itbookfront.signin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.signin.dto.request.MemberInputRequestDto;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;
import shop.itbook.itbookfront.signin.service.SignUpService;

/**
 * 회원가입 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController{

    private final SignUpService signUpService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping()
    public String signupForm(@ModelAttribute("memberInputRequestDto")
                             MemberInputRequestDto memberInputRequestDto) {
        return "signuppage/signup";
    }

    @PostMapping()
    public String register(@Valid MemberInputRequestDto memberInputRequestDto, Errors errors, Model model) {

        if(errors.hasErrors()) {

            model.addAttribute("memberInputRequestDto", memberInputRequestDto);

            Map<String, String> validatorResult = validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "signuppage/signup";
        }

        signUpService.addMember(memberInputRequestDto);

        return "redirect:/";
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors())
        {
            String fieldName = String.format("valid_%s",error.getField());
            validatorResult.put(fieldName,error.getDefaultMessage());
        }

        return validatorResult;
    }
}
