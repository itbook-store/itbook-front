package shop.itbook.itbookfront.product.controller.serviceapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.exception.ProductNotFoundException;
import shop.itbook.itbookfront.product.service.ProductService;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookfront.review.exception.ReviewNotFoundException;
import shop.itbook.itbookfront.review.service.ReviewService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductServiceController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final ProductInquiryService productInquiryService;
    private final MemberService memberService;

    private final String DAILYHITS_COOKIENAME = "ITBOOK-VIEW";


    @GetMapping(params = {"categoryNo", "categoryName"})
    public String productListByCategory(@RequestParam Integer categoryNo,
                                        @RequestParam String categoryName,
                                        Model model, @PageableDefault Pageable pageable,
                                        RedirectAttributes redirectAttributes) {
        try {
            PageResponse<CategoryListResponseDto> pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories?page=%d&size=%d",
                        PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT));
            List<MainCategory> mainCategoryList =
                CategoryUtil.getMainCategoryList(pageResponse.getContent());
            model.addAttribute("mainCategoryList", mainCategoryList);

            List<ProductTypeResponseDto> productTypeList = productService.findProductTypeList(
                "/api/products/product-types?page=0&size=" + Integer.MAX_VALUE).getContent();
            model.addAttribute("productTypeList", productTypeList);

            PageResponse<ProductDetailsResponseDto> productList =
                productService.getProductList(
                    String.format("/api/admin/products?page=%d&size=%d&categoryNo=%d",
                        pageable.getPageNumber(), pageable.getPageSize(), categoryNo));
            model.addAttribute("pageResponse", productList);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        model.addAttribute("categoryName", categoryName);

        model.addAttribute("paginationUrl",
            String.format("/products?categoryNo=%d&categoryName=%s", categoryNo,
                categoryName));

        return "mainpage/product/product-category";
    }


    @GetMapping(params = {"productTypeNo", "productTypeName"})
    public String productListByProductType(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                           @RequestParam Integer productTypeNo,
                                           @RequestParam String productTypeName,
                                           Model model, @PageableDefault Pageable pageable,
                                           RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse =
            categoryService.findCategoryList(
                String.format("/api/admin/categories?page=%d&size=%d",
                    PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT));
        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(pageResponse.getContent());
        model.addAttribute("mainCategoryList", mainCategoryList);

        try {

            List<ProductTypeResponseDto> productTypeList = productService.findProductTypeList(
                "/api/products/product-types?page=0&size=" + Integer.MAX_VALUE).getContent();
            model.addAttribute("productTypeList", productTypeList);

            if (Optional.ofNullable(userDetailsDto).isPresent()) {
                Long memberNo = userDetailsDto.getMemberNo();
                PageResponse<ProductDetailsResponseDto> productList =
                    productService.getProductList(
                        String.format("/api/products?productTypeNo=%d&memberNo=%d&page=%d&size=%d",
                            productTypeNo, memberNo, pageable.getPageNumber(),
                            pageable.getPageSize()));
                model.addAttribute("pageResponse", productList);
            } else {
                PageResponse<ProductDetailsResponseDto> productList =
                    productService.getProductList(
                        String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                            pageable.getPageNumber(), pageable.getPageSize(), productTypeNo));
                model.addAttribute("pageResponse", productList);
            }
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        model.addAttribute("productTypeName", productTypeName);

        model.addAttribute("paginationUrl",
            String.format("/products?productTypeNo=%d&productTypeName=%s", productTypeNo,
                productTypeName));

        return "mainpage/product/product-producttype";
    }

    @GetMapping("/{productNo}")
    public String getProductDetails(@PathVariable Long productNo, Model model,
                                    RedirectAttributes redirectAttributes,
                                    @PageableDefault Pageable pageable,
                                    @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        productService.checkCookieForDailyHits(productNo, request, response);

        PageResponse<CategoryListResponseDto> pageResponse =
            categoryService.findCategoryList(String.format("/api/admin/categories?page=%d&size=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT));
        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(pageResponse.getContent());
        model.addAttribute("mainCategoryList", mainCategoryList);

        List<ProductTypeResponseDto> productTypeList = productService.findProductTypeList(
            "/api/products/product-types?page=0&size=" + Integer.MAX_VALUE).getContent();
        model.addAttribute("productTypeList", productTypeList);

        try {
            ProductDetailsResponseDto product = productService.getProduct(productNo);
            model.addAttribute("product", product);

            PageResponse<ProductDetailsResponseDto> relationProductList =
                productService.getProductList(
                    String.format("/api/admin/products/relation/%d?page=%d&size=%d",
                        productNo, pageable.getPageNumber(), pageable.getPageSize()));
            model.addAttribute("pageResponse", relationProductList);

            PageResponse<ReviewResponseDto> reviewPageResponse =
                reviewService.findReviewListByProductNo(
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()),
                    productNo);

            double avgStarPoint = reviewService.calculateStarAvg(reviewPageResponse);

            model.addAttribute("reviewPageResponse", reviewPageResponse);
            model.addAttribute("reviewPaginationUrl", "/products/" + productNo);

            model.addAttribute("avgStarPoint", avgStarPoint);

            if (userDetailsDto != null) {
                model.addAttribute("memberIdLoggedIn", userDetailsDto.getMemberId());
                model.addAttribute("memberName",
                    memberService.findMember(userDetailsDto.getMemberNo()).getName());
                model.addAttribute("memberIsWriter",
                    memberService.findMember(userDetailsDto.getMemberNo()).getIsWriter());
            }
            PageResponse<ProductInquiryResponseDto> productInquiryResponse =
                productInquiryService.findProductInquiryListByProductNo(
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()),
                    productNo);

            model.addAttribute("productInquiryPageResponse", productInquiryResponse);
            model.addAttribute("productPaginationUrl", "/products/" + productNo);

        } catch (BadRequestException e) {

            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "mainpage/product/product-details";
    }
}
