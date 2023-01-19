package shop.itbook.itbookfront.elastic.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.elastic.dto.response.ProductSampleResponseDto;
import shop.itbook.itbookfront.elastic.service.ProductSearchService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequestMapping("/product/search")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchService;
    private static final String DIRECTORY_NAME = "product";

    @GetMapping
    public List<ProductSampleResponseDto> nameList(@RequestParam String name) {

        List<ProductSampleResponseDto> productList = productSearchService.findProductList("/api/products/search?name="+name);

//        model.addAttribute("productList", productList);
//        return Strings.concat(DIRECTORY_NAME, "/categoryList");
        return productList;
    }
}
