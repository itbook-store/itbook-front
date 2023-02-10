package shop.itbook.itbookfront.pointhistory.controller.adminapi;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.pointhistory.adapter.PointHistoryDetailsGetAdaptor;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryDetailsGiftResponseDto;

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
    public String giftDetails(@PathVariable Long pointHistoryNo, Model model) {
        PointHistoryDetailsGiftResponseDto pointHistoryDetailsGiftResponseDto =
            pointHistoryDetailsGetAdaptor.findPointHistoryDetailsGift(
                "/api/admin/point-histories/" + pointHistoryNo + "/gift-details");

        model.addAttribute("details", pointHistoryDetailsGiftResponseDto);

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
    public String gradeDetails(@PathVariable Long pointHistoryNo, Model model) {


        return "mypage/member/membership-info";
    }


    @GetMapping("/{pointHistoryNo}/coupon-details")
    public String couponDetails(@PathVariable Long pointHistoryNo, Model model) {

        return "내가만든 뷰";
    }



    @GetMapping("/{pointHistoryNo}/review-details")
    public String reviewDetails(@PathVariable Long pointHistoryNo) {

        // 가져오는로직
        return "수연님이 만든 뷰";
    }


}
