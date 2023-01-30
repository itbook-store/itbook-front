package shop.itbook.itbookfront.product.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping(params = {"categoryNo", "categoryName"})
    public String productListByCategory(@RequestParam Integer categoryNo,
                                        @RequestParam String categoryName,
                                        Model model) {
        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/categories").getContent();
        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);
        List<ProductDetailsResponseDto> productList =
            productService.getProductListFilteredByCategoryNo(categoryNo);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("productList", productList);
        return "mainpage/product-category";
    }

    @GetMapping("/{productNo}")
    public String getAddProductForm(@PathVariable Long productNo, Model model) {
        model.addAttribute("product",
            productService.getProduct(productNo));
        return "mainpage/product-details";
    }

}
