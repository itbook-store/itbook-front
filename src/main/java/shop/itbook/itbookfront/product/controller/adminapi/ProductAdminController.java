package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.BookRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
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
    private static final String PRODUCT_REDIRECT_URL = "redirect:/admin/products";


    @GetMapping
    public String getAdminProductPage(Model model, @PageableDefault Pageable pageable) {
        PageResponse<ProductDetailsResponseDto> pageResponse
            = productService.getProductList(String.format("/api/admin/products?page=%d&size=%d",
            pageable.getPageNumber(), pageable.getPageSize()));
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/products");
        return "adminpage/product/product-management";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute BookRequestDto requestDto,
                             @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                             @RequestPart(value = "fileEbook", required = false)
                             MultipartFile ebook) {
        productService.addBook(thumbnails, ebook, requestDto);
        return PRODUCT_REDIRECT_URL;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductRequestDto requestDto,
                             @RequestPart(value = "fileThumbnails") MultipartFile thumbnails) {
        productService.addProduct(thumbnails, requestDto);
        return PRODUCT_REDIRECT_URL;
    }

    @PostMapping("/{productNo}/modify")
    public String modifyProduct(@PathVariable Long productNo,
                                @ModelAttribute BookRequestDto requestDto,
                                @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                                @RequestPart(value = "fileEbook", required = false)
                                MultipartFile ebook) {
        productService.modifyProduct(productNo, thumbnails, ebook, requestDto);
        return PRODUCT_REDIRECT_URL;
    }

    @GetMapping("/{productNo}/delete")
    public String deleteProduct(@PathVariable Long productNo) {
        productService.removeProduct(productNo);
        return PRODUCT_REDIRECT_URL;
    }

    @GetMapping("/books/add")
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

    @GetMapping("/add")
    public String getAddProductForm(Model model) {
        List<CategoryListResponseDto> allCategoryList = categoryService.findCategoryList(
            String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();

        List<CategoryListResponseDto> categoryList = allCategoryList.stream()
            .filter(c -> !c.getCategoryName().contains("도서"))
            .collect(Collectors.toList());

        model.addAttribute("mainCategoryList", categoryList);

        return "adminpage/product/product-add";
    }

    @GetMapping("/{productNo}/modify")
    public String getModifyProductForm(Model model, @PathVariable Long productNo) {
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

        return "adminpage/product/product-modify";
    }

}
