package shop.itbook.itbookfront.pointhistory.controller.adminapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.pointhistory.adapter.PointHistoryDetailsGetAdaptor;
import shop.itbook.itbookfront.pointhistory.dto.response.details.PointHistoryCouponDetailsResponseDto;
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
@Slf4j
public class PointHistoryAdminDetailsGetController {

    private final PointHistoryDetailsGetAdaptor pointHistoryDetailsGetAdaptor;
    public static final String ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME = "adminpage/pointhistory/details";

    @GetMapping("/{pointHistoryNo}/gift-details")
    public String giftDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            PointHistoryDetailsGiftResponseDto pointHistoryDetailsGiftResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
                    "/api/admin/point-histories/" + pointHistoryNo + "/gift-details");

            model.addAttribute("details", pointHistoryDetailsGiftResponseDto);
            model.addAttribute("adminPointHistoryPage", session.getAttribute("adminPointHistoryPage"));

            if (pointHistoryDetailsGiftResponseDto.getIsDecrease()) {
                return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/adminGiftDetailsDecrease");
            }
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage/point-histories/show-content/admin-point-list";
        }


        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/adminGiftDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/order-details")
    public String orderDetails(@PathVariable Long pointHistoryNo, RedirectAttributes redirectAttributes) {
        try {
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage/point-histories/show-content/admin-point-list";
        }

        return "ing";
    }

    @GetMapping("/{pointHistoryNo}/grade-details")
    public String gradeDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            PointHistoryGradeDetailsResponseDto pointHistoryGradeDetailsResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGrade(
                    "/api/admin/point-histories/" + pointHistoryNo + "/grade-details");

            model.addAttribute("details", pointHistoryGradeDetailsResponseDto);
            model.addAttribute("adminPointHistoryPage", session.getAttribute("adminPointHistoryPage"));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage/point-histories/show-content/admin-point-list";
        }

        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/grade/adminGradeDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/coupon-details")
    public String couponDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsCoupon(
                    "/api/admin/point-histories/" + pointHistoryNo + "/coupon-details");

            model.addAttribute("details", pointHistoryCouponDetailsResponseDto);
            model.addAttribute("adminPointHistoryPage", session.getAttribute("adminPointHistoryPage"));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage/point-histories/show-content/admin-point-list";
        }

        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/coupon/adminGradeDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/review-details")
    public String reviewDetails(@PathVariable Long pointHistoryNo, Model model, RedirectAttributes redirectAttributes) {

        try {
            ReviewResponseDto reviewResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsReview(
                    "/api/admin/point-histories/" + pointHistoryNo + "/review-details");

            model.addAttribute("reviewResponseDto", reviewResponseDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/adminpage/point-histories/show-content/admin-point-list";
        }

        return Strings.concat(ADMIN_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/review/adminReviewDetailsIncrease");
    }
}
