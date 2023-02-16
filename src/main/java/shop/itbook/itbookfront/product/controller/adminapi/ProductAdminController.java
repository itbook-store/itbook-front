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
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.exception.InvalidInputException;
import shop.itbook.itbookfront.product.service.ProductService;
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
    public String getAdminProductPage(Model model, @PageableDefault Pageable pageable
        , RedirectAttributes redirectAttributes) {
        try {
            PageResponse<ProductDetailsResponseDto> pageResponse
                = productService.getProductList(String.format("/api/admin/products?page=%d&size=%d",
                pageable.getPageNumber(), pageable.getPageSize()));
            model.addAttribute("pageResponse", pageResponse);

        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_REDIRECT_URL;
        }

        model.addAttribute("paginationUrl", "/admin/products");
        return "adminpage/product/product-management";
    }

    @GetMapping("/{productNo}/delete")
    public String deleteProduct(@PathVariable Long productNo) {
        productService.changeBooleanField(productNo, "delete");
        return PRODUCT_REDIRECT_URL;
    }

    @GetMapping("/{productNo}/force-sold-out")
    public String forceSoldOutProduct(@PathVariable Long productNo) {
        productService.changeBooleanField(productNo, "isForceSoldOut");
        return PRODUCT_REDIRECT_URL;
    }

    @GetMapping("/{productNo}/stop-selled")
    public String stopSelledProduct(@PathVariable Long productNo) {
        productService.changeBooleanField(productNo, "isSelled");
        return PRODUCT_REDIRECT_URL;
    }

    @GetMapping("/add")
    public String getAddProductForm(Model model, RedirectAttributes redirectAttributes) {

        try {
            List<CategoryListResponseDto> allCategoryList = categoryService.findCategoryList(
                String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                    PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();

            List<CategoryListResponseDto> categoryList = allCategoryList.stream()
                .filter(c -> !c.getCategoryName().contains("도서"))
                .collect(Collectors.toList());

            model.addAttribute("mainCategoryList", categoryList);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_REDIRECT_URL;
        }
        return "adminpage/product/product-add";
    }


    @GetMapping("/{productNo}/modify")
    public String getModifyProductForm(Model model, @PathVariable Long productNo) {
        setUpToModify(model, productNo, productService, categoryService);

        return "adminpage/product/product-modify";
    }

    @PostMapping("/{productNo}/modify")
    public String modifyProduct(@PathVariable Long productNo,
                                @ModelAttribute @Valid ProductModifyRequestDto requestDto,
                                @RequestPart(value = "fileThumbnails", required = false)
                                MultipartFile thumbnails, RedirectAttributes redirectAttributes) {
        try {
            productService.modifyProduct(productNo, thumbnails, requestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_REDIRECT_URL;
        }
        return PRODUCT_REDIRECT_URL;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("postForm") @Valid ProductAddRequestDto requestDto,
                             @RequestPart(value = "fileThumbnails") MultipartFile thumbnails,
                             RedirectAttributes redirectAttributes) {

        try {
            productService.addProduct(thumbnails, requestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return PRODUCT_REDIRECT_URL;
        }
        return PRODUCT_REDIRECT_URL;
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
