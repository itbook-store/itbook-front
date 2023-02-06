package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
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
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.BookRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.exception.InvalidInputException;
import shop.itbook.itbookfront.product.service.impl.ProductServiceImpl;

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
    private final ProductServiceImpl productService;
    private final CategoryService categoryService;
    private static final String PRODUCT_REDIRECT_URL = "redirect:/admin/products";


    @PostMapping("/add")
    public String addBook(@ModelAttribute @Valid BookRequestDto requestDto,
                          @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                          @RequestPart(value = "fileEbook", required = false)
                          MultipartFile ebook, RedirectAttributes redirectAttributes) {

        if (BindException != null)

            try {
                productService.addBook(thumbnails, ebook, requestDto);
            } catch (InvalidInputException e) {
                redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            }
        return PRODUCT_REDIRECT_URL;
    }


    @GetMapping("/add")
    public String getAddBookForm(Model model) {
        List<CategoryListResponseDto> allCategoryList = categoryService.findCategoryList(
            String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();

        List<CategoryListResponseDto> categoryList = allCategoryList.stream()
            .filter(c -> c.getCategoryName().contains("도서"))
            .collect(Collectors.toList());

        model.addAttribute("mainCategoryList", categoryList);
        return "adminpage/product/book-add";
    }

}
