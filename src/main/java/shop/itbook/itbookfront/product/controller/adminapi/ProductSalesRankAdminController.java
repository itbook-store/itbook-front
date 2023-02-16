package shop.itbook.itbookfront.product.controller.adminapi;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.productenum.ProductSortingCriteriaEnum;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products/sales-rank")
public class ProductSalesRankAdminController {

    private final ProductService productService;

    @GetMapping
    public String getSortingCriteriaRank() {

        return "adminpage/product/product-sales-rank";
    }

    @GetMapping(params = "sortingCriteria")
    public String getProductListBySortingCriteria(Model model, @RequestParam String sortingCriteria,
                                                  @PageableDefault Pageable pageable,
                                                  RedirectAttributes redirectAttributes) {
        try {
            PageResponse<ProductSalesRankResponseDto> pageResponse =
                productService.findSalesRankProductList(pageable, sortingCriteria);

            model.addAttribute("pageResponse", pageResponse);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/products/sales-rank";
        }
        model.addAttribute("sortingCriteria", sortingCriteria);

        model.addAttribute("paginationUrl",
            "/admin/products/sales-rank?sortingCriteria=" + sortingCriteria);

        return "adminpage/product/product-sales-rank-details";
    }

}
