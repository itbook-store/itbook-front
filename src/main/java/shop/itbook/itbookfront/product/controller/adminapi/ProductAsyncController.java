package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.service.BookService;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/products")
public class ProductAsyncController {
    private final ProductService productService;

    @GetMapping(value = "/sales-rank", params = "sortingCriteria")
    public List<ProductSalesRankResponseDto> getProductListBySortingCriteria(
        @RequestParam String sortingCriteria) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return productService.findSalesRankProductList(pageable, sortingCriteria).getContent();
    }

    @GetMapping(value = "/categories/{productNo}")
    public List<CategoryDetailsResponseDto> getCategories(
        @PathVariable Long productNo) {
        return productService.getCategoryList(
            String.format("/api/admin/products?page=%d&size=%d&productNo=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT, productNo)).getContent();
    }

}
