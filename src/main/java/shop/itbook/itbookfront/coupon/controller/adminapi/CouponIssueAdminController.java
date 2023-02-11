package shop.itbook.itbookfront.coupon.controller.adminapi;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookfront.coupon.exception.InvalidPathRequestCouponList;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponIssueAdminService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/coupon/coupon-issues")
@RequiredArgsConstructor
public class CouponIssueAdminController {

    private final CouponIssueAdminService couponIssueAdminService;
    private final CategoryService categoryService;
    private static final String DIRECTORY_NAME = "adminpage/couponadmin";

    @GetMapping("/list")
    public String couponList(Model model, @PageableDefault Pageable pageable){

        PageResponse<AdminCouponIssueListResponseDto> couponIssueList =
                couponIssueAdminService.findAllCouponIssueList(
                    String.format("/api/admin/coupon-issues?page=%d&size=%d",
                        pageable.getPageNumber(), pageable.getPageSize()));
            model.addAttribute("pageResponse", couponIssueList);
            model.addAttribute("paginationUrl", "/admin/coupon/coupon-issues/list");
            return Strings.concat(DIRECTORY_NAME, "/couponList");
    }
}
