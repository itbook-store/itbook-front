package shop.itbook.itbookfront.product.controller.adminapi;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;
import shop.itbook.itbookfront.product.service.impl.ProductServiceImpl;

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
    private final ProductServiceImpl productService;
    private final CategoryService categoryService;

    @PostMapping
    public String addBook(@ModelAttribute AddProductBookRequestDto requestDto,
                          @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                          @RequestPart(value = "fileEbook", required = false) MultipartFile ebook) {

        productService.addBook(thumbnails, ebook, requestDto);

        return "redirect:/admin/products/management";
    }

    @GetMapping("/management")
    public String adminProductPage(Model model) throws IOException {
        List<GetBookResponseDto> bookList = productService.getBookList();
        model.addAttribute("productList", bookList);
        return "adminpage/product/product-management";
    }

    @GetMapping
    public String getAddProductForm(Model model) throws IOException {
        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList("/api/admin/categories/main-categories"));
        return "adminpage/product/product-add";
    }

}
