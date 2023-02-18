package shop.itbook.itbookfront.product.controller.adminapi;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products")
public class ProductTypeAdminController {
    private final ProductService productService;
    private static final String PRODUCT_REDIRECT_URL = "redirect:/admin/products";

    @GetMapping(params = {"productTypeNo", "productTypeName"})
    public String productListByProductTypeForAdmin(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @RequestParam Integer productTypeNo,
        @RequestParam String productTypeName,
        Model model, @PageableDefault Pageable pageable,
        RedirectAttributes redirectAttributes) {

        try {
            if (Optional.ofNullable(userDetailsDto).isPresent()) {
                Long memberNo = userDetailsDto.getMemberNo();
                PageResponse<ProductDetailsResponseDto> productList =
                    productService.getProductList(
                        String.format(
                            "/api/admin/products?productTypeNo=%d&memberNo=%d&page=%d&size=%d",
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
            return PRODUCT_REDIRECT_URL;
        }

        model.addAttribute("productTypeName", productTypeName);
        model.addAttribute("paginationUrl",
            String.format("/admin/products?productTypeNo=%d&productName=%s", productTypeNo,
                productTypeName));
        return "adminpage/product/product-management";
    }

}
