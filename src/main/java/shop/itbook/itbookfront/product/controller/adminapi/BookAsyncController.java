package shop.itbook.itbookfront.product.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.service.BookService;
import shop.itbook.itbookfront.product.service.ProductService;
import shop.itbook.itbookfront.signin.dto.response.MemberBooleanResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/books")
@Slf4j
public class BookAsyncController {
    private final BookService bookService;

    @GetMapping
    public SearchBookDetailsDto searchBookDetailsInAladin(@RequestParam String isbn,
                                                          RedirectAttributes redirectAttributes) {

        SearchBookDetailsDto searchBookDetailsDto = null;
        try {
            bookService.searchBook("/api/admin/products/books?isbn=" + isbn);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return searchBookDetailsDto;
    }

    @GetMapping("exist-db")
    public ProductBooleanResponseDto checkIsbnExistsInDb(@RequestParam String isbn,
                                                         RedirectAttributes redirectAttributes) {

        ProductBooleanResponseDto productBooleanResponseDto = null;

        try {
            productBooleanResponseDto = bookService.checkIsbnExists(
                "/api/admin/products/books/check-exist-db?isbn=" + isbn);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return productBooleanResponseDto;
    }

    @GetMapping("exist-aladin")
    public ProductBooleanResponseDto checkIsbnExistsInAladin(@RequestParam String isbn,
                                                             RedirectAttributes redirectAttributes) {

        ProductBooleanResponseDto productBooleanResponseDto = null;

        try {
            productBooleanResponseDto = bookService.checkIsbnExists(
                "/api/admin/products/books/check-exist-aladin?isbn=" + isbn);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return productBooleanResponseDto;
    }

}
