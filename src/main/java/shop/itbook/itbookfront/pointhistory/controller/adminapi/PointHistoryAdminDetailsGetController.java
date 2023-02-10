package shop.itbook.itbookfront.pointhistory.controller.adminapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookfront.pointhistory.adapter.PointHistoryDetailsGetAdaptor;
import shop.itbook.itbookfront.pointhistory.dto.response.details.PointHistoryDetailsGiftResponseDto;
import shop.itbook.itbookfront.pointhistory.dto.response.details.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/point-histories")
public class PointHistoryAdminDetailsGetController {

    private final PointHistoryDetailsGetAdaptor pointHistoryDetailsGetAdaptor;
    public static final String ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME = "adminpage/pointhistory/details";

    @GetMapping("/{pointHistoryNo}/gift-details")
    public String giftDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session) {
        PointHistoryDetailsGiftResponseDto pointHistoryDetailsGiftResponseDto =
            pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
                "/api/admin/point-histories/" + pointHistoryNo + "/gift-details");

        model.addAttribute("details", pointHistoryDetailsGiftResponseDto);
        model.addAttribute("adminPointHistoryPage", session.getAttribute("adminPointHistoryPage"));

        if (pointHistoryDetailsGiftResponseDto.getIsDecrease()) {
            return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/adminGiftDetailsDecrease");
        }

        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/adminGiftDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/order-details")
    public String orderDetails(@PathVariable Long pointHistoryNo) {

        pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
                "/api/admin/point-histories/" + pointHistoryNo + "/order-details");

        return "재원님이 만든 page";
    }

    @GetMapping("/{pointHistoryNo}/order-cancel-details")
    public String orderCancelDetails(@PathVariable Long pointHistoryNo) {

        pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
            "/api/admin/point-histories/" + pointHistoryNo + "/order-details");

        return "재원님";
    }

    @GetMapping("/{pointHistoryNo}/grade-details")
    public String gradeDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session) {

        PointHistoryGradeDetailsResponseDto pointHistoryGradeDetailsResponseDto =
            pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGrade(
                "/api/admin/point-histories/" + pointHistoryNo + "/grade-details");


        model.addAttribute("details", pointHistoryGradeDetailsResponseDto);
        model.addAttribute("adminPointHistoryPage", session.getAttribute("adminPointHistoryPage"));

        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/grade/adminGradeDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/coupon-details")
    public String couponDetails(@PathVariable Long pointHistoryNo, Model model) {

        return "내가만든 뷰";
    }

    @GetMapping("/{pointHistoryNo}/review-details")
    public String reviewDetails(@PathVariable Long pointHistoryNo, Model model) {

        ReviewResponseDto reviewResponseDto =
            pointHistoryDetailsGetAdaptor.findPointHistoryDetailsReview(
                "/api/admin/point-histories/" + pointHistoryNo + "/review-details");

        model.addAttribute("reviewResponseDto", reviewResponseDto);
        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "mypage/review/review-detail");
    }
}
