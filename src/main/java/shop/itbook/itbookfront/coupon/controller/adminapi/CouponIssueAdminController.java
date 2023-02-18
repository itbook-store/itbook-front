package shop.itbook.itbookfront.coupon.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponIssueAdminService;
import shop.itbook.itbookfront.member.dto.request.MemberSearchRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/coupon/coupon-issues")
@RequiredArgsConstructor
public class CouponIssueAdminController {

    private final CouponIssueAdminService couponIssueAdminService;
    private static final String DIRECTORY_NAME = "adminpage/couponadmin";

    @GetMapping("/list")
    public String couponList(Model model, @PageableDefault Pageable pageable) {

        PageResponse<AdminCouponIssueListResponseDto> couponIssueList =
            couponIssueAdminService.findAllCouponIssueList(
                String.format("/list?page=%d&size=%d", pageable.getPageNumber(),
                    pageable.getPageSize()));
        model.addAttribute("pageResponse", couponIssueList);
        model.addAttribute("paginationUrl", "/admin/coupon/coupon-issues/list");
        return Strings.concat(DIRECTORY_NAME, "/couponIssueList");
    }

    @GetMapping("/search")
    public String memberSearch(Model model, @PageableDefault Pageable pageable,
                               String searchTarget, String keyword) {

        PageResponse<AdminCouponIssueListResponseDto> couponIssueList =
            couponIssueAdminService.findAllCouponIssueList(
                String.format("/search?page=%d&size=%d&searchTarget=%s&keyword=%s",
                    pageable.getPageNumber(), pageable.getPageSize(), searchTarget, keyword));
        model.addAttribute("pageResponse", couponIssueList);
        model.addAttribute("paginationUrl",
            String.format("/admin/coupon/coupon-issues/search?searchTarget=%s&keyword=%s",
                searchTarget, keyword));
        return Strings.concat(DIRECTORY_NAME, "/couponIssueList");
    }
}
