package shop.itbook.itbookfront.coupon.controller.adminapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookfront.coupon.exception.CategoryNumberNotFoundException;
import shop.itbook.itbookfront.coupon.exception.CouponCoverageNotSelectException;
import shop.itbook.itbookfront.coupon.exception.MembershipGradeNotFoundException;
import shop.itbook.itbookfront.coupon.exception.ProductCouponNumberNotFoundException;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponService;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.exception.InvalidPathRequestCouponList;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookfront.membership.service.MembershipService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponService couponService;
    private final CategoryService categoryService;
    private final MembershipService membershipService;
    private static final String DIRECTORY_NAME = "adminpage/couponadmin";

    @GetMapping("/coupon-addition")
    public String couponAddPage(@ModelAttribute("couponInputRequestDto")
                                    CouponInputRequestDto couponInputRequestDto, Model model){
        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList("/api/admin/categories/main-categories").getContent());
        model.addAttribute("membershipList", membershipService.getMemberships());

        return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
    }

    @PostMapping ("/coupon-addition")
    public String couponAdd(@ModelAttribute @Valid CouponInputRequestDto couponInputRequestDto, Model model,
                            Errors errors, @RequestParam String couponCoverageGroup,
                            @RequestParam(required = false) String memberId,
                            @RequestParam(required = false) String membership,
                            RedirectAttributes redirectAttributes) {

        if(Objects.isNull(couponInputRequestDto.getTotalQuantity())){
            couponInputRequestDto.setTotalQuantity(0L);
        }
        couponInputRequestDto.setDuplicateUse(false);

        try {
            Long couponNo = couponType(couponCoverageGroup, couponInputRequestDto);
            if (couponInputRequestDto.getCouponType().equals("이달의쿠폰등급형")) {
                couponService.addMembershipCoupon(couponNo, membership);
            }
        } catch (BadRequestException e) {

            redirectAttributes.addFlashAttribute("couponInputRequestDto", couponInputRequestDto);
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/coupons/coupon-addition";
        }

        return "redirect:/admin/coupons/list";
    }

    private Long couponType(String coverage, CouponInputRequestDto couponInputRequestDto){
        if (coverage.equals("point")){
            return couponService.addCoupon(couponInputRequestDto);
        } else if(coverage.equals("category")){
            return couponService.addCategoryCoupon(couponInputRequestDto);
        } else if (coverage.equals("product")) {
            return couponService.addProductCoupon(couponInputRequestDto);
        } else if (coverage.equals("total")){
            return couponService.addOrderTotalCoupon(couponInputRequestDto);
        } else {
            throw new BadRequestException("잘못된 요청입니다.");
        }
    }

    @GetMapping("/list")
    public String couponList(Model model, @RequestParam(required = false) String coverage,
                             @PageableDefault Pageable pageable) throws InvalidPathRequestCouponList {

        PageResponse<AdminCouponListResponseDto> couponList = null;
        if(Objects.isNull(coverage)) {
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/coupons?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
            model.addAttribute("pageResponse", couponList);
            model.addAttribute("paginationUrl", "/admin/coupons/list");
            return Strings.concat(DIRECTORY_NAME, "/couponList");
        }
        else if(coverage.equals("total")){
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/order-total-coupons/list?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
        } else if(coverage.equals("product")){
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/product-coupons/list?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
        } else if(coverage.equals("category")){
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/category-coupons/list?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
        }
        else{
            throw new BadRequestException("잘못된 요청입니다.");
        }
        model.addAttribute("pageResponse", couponList);
        model.addAttribute("paginationUrl",
            String.format("/admin/coupons/list?coverage=%s", coverage));
        return Strings.concat(DIRECTORY_NAME, "/couponList");
    }
}
