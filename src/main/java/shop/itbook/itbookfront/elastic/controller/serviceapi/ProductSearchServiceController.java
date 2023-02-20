package shop.itbook.itbookfront.elastic.controller.serviceapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.elastic.dto.response.ProductSampleResponseDto;
import shop.itbook.itbookfront.elastic.service.ProductSearchService;

/**
 * 상품 검색을 담당하는 컨트롤러입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/products/search")
@RequiredArgsConstructor
public class ProductSearchServiceController {
    private final ProductSearchService productSearchService;
    private final CategoryService categoryService;

    /**
     * 이름으로 상품을 검색하여 리스트를 반환하는 메소드입니다.
     *
     * @param name 검색할 내용을 받습니다.
     * @return 상품정보를 반환해줍니다.
     */
    @GetMapping
    public String searchProductByName(@RequestParam String name, Model model,
                                      @PageableDefault Pageable pageable) {
        if (name.length() > 30) {
            name = name.substring(0, 30);
            String searchTermsIgnored = name.substring(23);
            model.addAttribute("searchTermsIgnored", searchTermsIgnored);
        }

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryService.findCategoryListForUser());
        model.addAttribute("mainCategoryList", mainCategoryList);

        PageResponse<ProductSampleResponseDto> productList =
            productSearchService.findProductPageList(
                String.format("/api/products/search?name=%s&page=%d&size=%d",
                    name, pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", productList);
        model.addAttribute("keyword", name);
        model.addAttribute("paginationUrl",
            String.format("/api/products/search?name=%s", name));

        return "mainpage/product/product-search";
    }
}
