package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products/relation")
public class ProductRelationAdminController {

    private final ProductService productService;

    // 모든 연관상품을 조회하는 화면을 불러옵니다.
    @GetMapping
    public String getAllProductRelationList(Model model, @PageableDefault Pageable pageable) {

        PageResponse<ProductRelationResponseDto> relationProductList =
            productService.findRelationProductList(
                String.format("/api/admin/products/relation?page=%d&size=%d",
                    pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("pageResponse", relationProductList);
        model.addAttribute("paginationUrl", "/admin/products/relation");
        return "adminpage/product/product-relation-management";
    }

    // 해당번호에 연관상품을 조회하는 화면을 불러옵니다.
    @GetMapping("/{productNo}")
    public String getProductRelationListFilteredProductNo(Model model, @PathVariable Long productNo,
                                                          @PageableDefault Pageable pageable) {
        PageResponse<ProductDetailsResponseDto> relationProductList =
            productService.getProductList(
                String.format("/api/products/relation/%d?page=%d&size=%d",
                    productNo, pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("pageResponse", relationProductList);
        model.addAttribute("basedProductNo", productNo);
        return "adminpage/product/product-relation-details";

    }


    // 해당번호에 연관상품 추가하는 화면을 불러옵니다.
    @GetMapping("/{basedProductNo}/edit")
    public String getAddProductRelationForm(Model model, @PathVariable Long basedProductNo,
                                            @PageableDefault Pageable pageable) {

        //추가할 후보들
        PageResponse<ProductDetailsResponseDto> candidateProductList =
            productService.getProductList(
                String.format("/api/products/relation/add-candidates/%d?page=%d&size=%d",
                    basedProductNo, pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("pageResponse", candidateProductList);

        //기존의 리스트
        List<ProductDetailsResponseDto> existingProductList =
            productService.getProductList(
                String.format("/api/products/relation/%d?page=%d&size=%d",
                    basedProductNo, PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();
        model.addAttribute("existingList", existingProductList);
        model.addAttribute("basedProductNo", basedProductNo);
        return "adminpage/product/product-relation-edit";
    }

    @PostMapping("/{basedProductNo}/edit")
    public String modifyProduct(@PathVariable Long basedProductNo,
                                @ModelAttribute ProductRelationRequestDto relationList) {
        productService.modifyRelationProduct(basedProductNo, relationList);
        return "redirect:/admin/products/relation/" + basedProductNo;
    }
}
