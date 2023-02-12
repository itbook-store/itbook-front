package shop.itbook.itbookfront.coupon.controller.adminapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponService;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.exception.InvalidPathRequestCouponList;

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
    private static final String DIRECTORY_NAME = "adminpage/couponadmin";

    @GetMapping("/coupon-addition")
    public String couponAddPage(@ModelAttribute("couponInputRequestDto")
                                    CouponInputRequestDto couponInputRequestDto, Model model){
        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList("/api/admin/categories/main-categories").getContent());
        return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
    }

    @PostMapping ("/coupon-addition")
    public String couponAdd(@Valid CouponInputRequestDto couponInputRequestDto, Model model, Errors errors) {

        if(errors.hasErrors()) {

            model.addAttribute("couponInputRequestDto", couponInputRequestDto);

            Map<String, String> validatorResult = validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return Strings.concat(DIRECTORY_NAME, "/couponAddForm");
        }
        couponInputRequestDto.setDuplicateUse(false);


        couponType(couponInputRequestDto);

        if (couponInputRequestDto.getCouponType().equals("이달의쿠폰등급형")) {
        }
        return "redirect:/admin/coupons";
    }

    private void couponType(CouponInputRequestDto couponInputRequestDto){
        if (couponInputRequestDto.getPoint() != 0 ){
            couponService.addCoupon(couponInputRequestDto);
//        }
//        else if("category"){
//            couponService.addCategoryCoupon(couponInputRequestDto);
//        }
//        else if ("product") {
//            couponService.addProductCoupon(couponInputRequestDto);
        } else {
            couponService.addOrderTotalCoupon(couponInputRequestDto);
        }
    }
    @PostMapping("/coupon-addition/coupon-type")
    public String couponTypeAdd(){
//         "먼슬리 쿠폰으로 가는 로직추가";
        return Strings.concat(DIRECTORY_NAME, "/gradecoupon");


    }
    @GetMapping
    public String couponList(Model model, @RequestParam(required = false) String coverage,
                             @PageableDefault Pageable pageable) throws InvalidPathRequestCouponList {

        PageResponse<CouponListResponseDto> couponList = null;
        if(Objects.isNull(coverage)) {
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/coupons?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
            model.addAttribute("pageResponse", couponList);
            model.addAttribute("paginationUrl", "/admin/coupons");
            return Strings.concat(DIRECTORY_NAME, "/couponList");
        }
        else if(coverage.equals("category")){
            couponList =
                couponService.findCouponList(
                    String.format("/api/admin/category-coupons?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
        }
        else{
            throw new InvalidPathRequestCouponList();
        }
        model.addAttribute("pageResponse", couponList);
        model.addAttribute("paginationUrl",
            String.format("/admin/coupons?coverage=%s", coverage));
        return Strings.concat(DIRECTORY_NAME, "/couponList");
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
