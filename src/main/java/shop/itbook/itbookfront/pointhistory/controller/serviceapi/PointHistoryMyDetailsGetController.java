package shop.itbook.itbookfront.pointhistory.controller.serviceapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
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
@RequestMapping("/point-histories")
@Slf4j
public class PointHistoryMyDetailsGetController {

    private final PointHistoryDetailsGetAdaptor pointHistoryDetailsGetAdaptor;
    public static final String MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME = "mypage/pointhistory/details";

    @GetMapping("/{pointHistoryNo}/gift-details")
    public String giftDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, @AuthenticationPrincipal
                              UserDetailsDto userDetailsDto, RedirectAttributes redirectAttributes) {
        try {

            PointHistoryDetailsGiftResponseDto pointHistoryDetailsGiftResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
                    "/api/point-histories/" + pointHistoryNo + "/my-gift-details/member-no/" + userDetailsDto.getMemberNo());


            model.addAttribute("details", pointHistoryDetailsGiftResponseDto);
            model.addAttribute("memberPointHistoryPage", session.getAttribute("memberPointHistoryPage"));

            if (pointHistoryDetailsGiftResponseDto.getIsDecrease()) {
                return Strings.concat(MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/myGiftDetailsDecrease");
            }
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/point-histories/show-content/my-point-list";
        }

        return Strings.concat(MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/gift/myGiftDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/order-details")
    public String orderDetails(@PathVariable Long pointHistoryNo, @AuthenticationPrincipal UserDetailsDto userDetailsDto, RedirectAttributes redirectAttributes) {

        try {
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/point-histories/show-content/my-point-list";
        }

        return "ing";
    }

    @GetMapping("/{pointHistoryNo}/grade-details")
    public String gradeDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, @AuthenticationPrincipal UserDetailsDto userDetailsDto, RedirectAttributes redirectAttributes) {

        try {

            PointHistoryGradeDetailsResponseDto pointHistoryGradeDetailsResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGrade(
                    "/api/point-histories/" + pointHistoryNo + "/my-grade-details/member-no/" + userDetailsDto.getMemberNo());

            model.addAttribute("details", pointHistoryGradeDetailsResponseDto);
            model.addAttribute("memberPointHistoryPage", session.getAttribute("memberPointHistoryPage"));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/point-histories/show-content/my-point-list";
        }

        return Strings.concat(MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/grade/myGradeDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/coupon-details")
    public String couponDetails(@PathVariable Long pointHistoryNo, Model model, HttpSession session, @AuthenticationPrincipal UserDetailsDto userDetailsDto, RedirectAttributes redirectAttributes) {

        try {
            PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsCoupon(
                    "/api/point-histories/" + pointHistoryNo + "/my-coupon-details/member-no/" + userDetailsDto.getMemberNo());


            model.addAttribute("details", pointHistoryCouponDetailsResponseDto);
            model.addAttribute("memberPointHistoryPage", session.getAttribute("memberPointHistoryPage"));

        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/point-histories/show-content/my-point-list";
        }

        return Strings.concat(MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/coupon/myGradeDetailsIncrease");
    }

    @GetMapping("/{pointHistoryNo}/review-details")
    public String reviewDetails(@PathVariable Long pointHistoryNo, Model model, @AuthenticationPrincipal UserDetailsDto userDetailsDto, RedirectAttributes redirectAttributes) {

        try {

            ReviewResponseDto reviewResponseDto =
                pointHistoryDetailsGetAdaptor.findPointHistoryDetailsReview(
                    "/api/point-histories/" + pointHistoryNo + "/my-review-details/member-no/" + userDetailsDto.getMemberNo());

            model.addAttribute("reviewResponseDto", reviewResponseDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/point-histories/show-content/my-point-list";
        }

        return Strings.concat(MY_PAGE_POINT_HISTORY_DETAILS_DIRECTORY_NAME, "/review/myReviewDetailsIncrease");
    }
}
