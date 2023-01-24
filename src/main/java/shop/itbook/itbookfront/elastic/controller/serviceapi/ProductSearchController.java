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
 * 상품 검색을 담당하는 컨트롤러입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
//TODO : 나중에 일반 컨트롤러로 변환 후 뷰 페이지로 보여주기
@RestController
@RequestMapping("/product/search")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchService;
    private static final String DIRECTORY_NAME = "product";

    /**
     * 이름으로 상품을 검색하여 리스트를 반환하는 메소드입니다.
     *
     * @param name 검색할 내용을 받습니다.
     * @return 상품정보를 반환해줍니다.
     */
    @GetMapping
    public List<ProductSampleResponseDto> searchProductByName(@RequestParam String name) {

        List<ProductSampleResponseDto> productList =
            productSearchService.findProductList("/api/products/search?name="+name);

//        model.addAttribute("productList", productList);
//        return Strings.concat(DIRECTORY_NAME, "/productList");
        return productList;
    }
}
