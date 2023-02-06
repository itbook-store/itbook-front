package shop.itbook.itbookfront.product.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.service.ProductService;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/books")
public class BookAsyncController {
    private final ProductService productService;

    @GetMapping
    public SearchBookDetailsDto searchBookDetailsInAladin(@RequestParam String isbn) {

        return productService.searchBook("/api/admin/products/books?isbn=" + isbn);
    }

    @GetMapping("exist-db")
    public ProductBooleanResponseDto checkIsbnExistsInDb(@RequestParam String isbn) {

        return productService.checkIsbnExists(
            "/api/admin/products/books/check-exist-db?isbn=" + isbn);
    }

    @GetMapping("exist-aladin")
    public ProductBooleanResponseDto checkIsbnExistsInAladin(@RequestParam String isbn) {

        return productService.checkIsbnExists(
            "/api/admin/products/books/check-exist-aladin?isbn=" + isbn);
    }
}
