package shop.itbook.itbookfront.product.controller.adminapi;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;
import shop.itbook.itbookfront.product.service.adminapi.ProductAdminService;

/**
 * The type Product admin controller.
 *
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products")
public class ProductAdminController {
    private final ProductAdminService productAdminService;

    /**
     * View add book form string.
     *
     * @return the string
     */
    @GetMapping("")
    public String viewAddBookForm() {
        return "adminpage/product/product-add";
    }


    /**
     * Add book string.
     *
     * @param requestDto the request dto
     * @param thumbnails the thumbnails
     * @param ebook      the ebook
     * @return
     * @
     */
    @PostMapping("/books")
    public String addBook(@ModelAttribute AddProductBookRequestDto requestDto,
                          @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                          @RequestPart(value = "fileEbook", required = false) MultipartFile ebook) {

        ProductNoResponseDto productNoResponseDto =
            productAdminService.addBook(thumbnails, ebook, requestDto);

        return "redirect:/admin/products/management";
    }

    /**
     * Admin product page string.
     *
     * @param model the model
     * @return the string
     * @throws IOException the io exception
     */
    @GetMapping("/management")
    public String adminProductPage(Model model) throws IOException {
        List<GetBookResponseDto> bookList = productAdminService.getBookList();
        model.addAttribute("productList", bookList);
        return "adminpage/product/product-management";
    }

    @GetMapping("/image/{id}")
    public void downloadThumbnails(@PathVariable Long id, Model model) {
        byte[] bytes = productAdminService.downloadThumbnails(id);
        model.addAttribute("img", Base64.getEncoder().encodeToString(bytes));
    }
}
