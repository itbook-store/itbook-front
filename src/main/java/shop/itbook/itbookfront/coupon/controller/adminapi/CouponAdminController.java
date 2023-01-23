package shop.itbook.itbookfront.coupon.controller.adminapi;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String couponAddSubCategory(Model model) {

        return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
    }
}
