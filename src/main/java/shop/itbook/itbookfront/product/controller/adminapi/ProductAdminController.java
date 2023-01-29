package shop.itbook.itbookfront.product.controller.adminapi;

import java.util.List;
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
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
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
    public String getAdminProductPage(Model model) {
        List<ProductDetailsResponseDto> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "adminpage/product/product-management";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductBookRequestDto requestDto,
                             @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                             @RequestPart(value = "fileEbook", required = false)
                             MultipartFile ebook) {
        productService.addBook(thumbnails, ebook, requestDto);
        return PRODUCT_REDIRECT_URL;
    }

    @PostMapping("/{productNo}/modify")
    public String modifyProduct(@PathVariable Long productNo,
                                @ModelAttribute ProductBookRequestDto requestDto,
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

    @GetMapping("/add")
    public String getAddProductForm(Model model) {
        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList("/api/admin/categories/main-categories"));
        return "adminpage/product/product-add";
    }

    @GetMapping("/{productNo}/modify")
    public String getModifyProductForm(Model model, @PathVariable Long productNo) {
        model.addAttribute("product", productService.getProduct(productNo));
        List<CategoryDetailsResponseDto> categoryListByProductNo =
            productService.getCategoryListFilteredByProductNo(productNo);
        model.addAttribute("productCategoryList", categoryListByProductNo);
        model.addAttribute("mainCategoryList",
            categoryService.findCategoryList("/api/admin/categories/main-categories"));
        model.addAttribute("parentCategoryName",
            categoryListByProductNo.get(0).getParentCategoryName());
        return "adminpage/product/product-modify";
    }

}
