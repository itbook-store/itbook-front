package shop.itbook.itbookfront.signin;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.signin.dto.request.MemberRequestDto;

/**
 * 회원가입 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Controller
@RequestMapping("/signup")
public class SignupController{

    @GetMapping()
    public String signupForm(@ModelAttribute("memberRequestDto") MemberRequestDto memberRequestDto) {
        return "signuppage/signup";
    }

    @PostMapping()
    public String register(@Valid MemberRequestDto memberRequestDto, Errors errors, RedirectAttributes redirectAttributes, Model model) {

        System.out.println("실행되었음");

        if(errors.hasErrors()) {
            model.addAttribute("memberRequestDto", memberRequestDto);

            Map<String, String> validatorResult = validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "signuppage/signup";
        }

        redirectAttributes.addAttribute("result", "valid value");
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
