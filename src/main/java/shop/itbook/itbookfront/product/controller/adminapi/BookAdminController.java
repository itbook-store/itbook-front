package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.product.ProductSuccessMessage;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.service.BookService;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * The type Book admin controller.
 *
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products/books")
public class BookAdminController {
    private final BookService bookService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private static final String PRODUCT_ADMIN_PAGE_REDIRECT_URL = "redirect:/admin/products";


    @PostMapping("/add")
    public String addBook(@ModelAttribute @Valid BookAddRequestDto requestDto,
                          @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                          @RequestPart(value = "fileEbook", required = false)
                          MultipartFile ebook, RedirectAttributes redirectAttributes) {

        try {
            bookService.addBook(thumbnails, ebook, requestDto);
        } catch (BadRequestException | NumberFormatException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
        }
        redirectAttributes.addFlashAttribute("successMessage",
            ProductSuccessMessage.ADD_BOOK_MESSAGE);

        return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
    }


    @GetMapping("/add")
    public String getAddBookForm(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<CategoryListResponseDto> allCategoryList = categoryService.findCategoryList(
                String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                    PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();

            List<CategoryListResponseDto> categoryList = allCategoryList.stream()
                .filter(c -> c.getCategoryName().contains("도서"))
                .collect(Collectors.toList());

            model.addAttribute("mainCategoryList", categoryList);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
        }
        return "adminpage/product/book-add";
    }

    @PostMapping("/{productNo}/modify")
    public String modifyBook(@ModelAttribute @Valid BookModifyRequestDto requestDto,
                             @PathVariable Long productNo,
                             @RequestPart(value = "fileThumbnails", required = false)
                             MultipartFile thumbnails,
                             @RequestPart(value = "fileEbook", required = false)
                             MultipartFile ebook, RedirectAttributes redirectAttributes) {
        try {
            bookService.modifyBook(productNo, thumbnails, ebook, requestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
        }
        redirectAttributes.addFlashAttribute("successMessage",
            ProductSuccessMessage.MODIFY_BOOK_MESSAGE);

        return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
    }


    @GetMapping("/{productNo}/modify")
    public String getModifyBookForm(Model model, @PathVariable Long productNo,
                                    RedirectAttributes redirectAttributes) {
        try {
            setUpToModify(model, productNo, productService, categoryService);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_ADMIN_PAGE_REDIRECT_URL;
        }
        return "adminpage/product/book-modify";
    }

    private void setUpToModify(Model model,
                               @PathVariable Long productNo,
                               ProductService productService,
                               CategoryService categoryService) {
        model.addAttribute("product", productService.getProduct(productNo));

        List<CategoryDetailsResponseDto> categoryListByProductNo =
            productService.getCategoryList(
                String.format("/api/admin/products?page=%d&size=%d&productNo=%d",
                    PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT, productNo)).getContent();
        model.addAttribute("productCategoryList", categoryListByProductNo);

        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList(
                String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                    PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent());

        model.addAttribute("parentCategoryName",
            categoryListByProductNo.get(0).getParentCategoryName());
    }

}
