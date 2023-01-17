package shop.itbook.itbookfront.product.controller.adminapi;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookListResponseDto;
import shop.itbook.itbookfront.product.service.adminapi.ProductAdminService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductAdminController {
    private final ProductAdminService productAdminService;

    @PostMapping("/admin/products/books")
    public String addBook(@Valid @ModelAttribute AddProductBookRequestDto requestDto,
                          @RequestPart("fileThumbnails") MultipartFile thumbnails,
                          @RequestPart(value = "fileEbook", required = false) MultipartFile ebook) {
        AddProductResponseDto addProductResponseDto =
            productAdminService.addBook(requestDto, thumbnails, ebook);
        log.info("{}", "add BookNo : " + addProductResponseDto.getProductNo());
        return "redirect:/admin/products/management";
    }

    @GetMapping("/admin/products/management")
    public String adminProductPage(Model model) {
        List<GetBookListResponseDto> bookList = productAdminService.getBookList();
        model.addAttribute("productList", bookList);
        return "adminpage/product/product-management";
    }

    @GetMapping("/admin/products")
    public String viewAddBookForm() {
        return "adminpage/product/product-add";
    }
}
