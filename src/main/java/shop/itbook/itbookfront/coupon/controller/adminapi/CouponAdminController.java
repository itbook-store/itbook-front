package shop.itbook.itbookfront.coupon.controller.adminapi;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/coupon")
@RequiredArgsConstructor
public class CouponAdminController {

    private static final String DIRECTORY_NAME = "couponadmin";

    @GetMapping("/coupon-addition")
    public String couponAddPage(@ModelAttribute("couponInputRequestDto")
                                    CouponInputRequestDto couponInputRequestDto){

        return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
    }

    @PostMapping ("/coupon-addition")
    public String couponAdd(@Valid CouponInputRequestDto couponInputRequestDto, Model model, Errors errors) {

        if(errors.hasErrors()) {

            model.addAttribute("couponInputRequestDto", couponInputRequestDto);

            Map<String, String> validatorResult = validateHandling(errors);
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
        }
        model.addAttribute("string", couponInputRequestDto.toString());


        return Strings.concat(DIRECTORY_NAME, "/test");
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
