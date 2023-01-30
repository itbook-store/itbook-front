package shop.itbook.itbookfront.ordersheet.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookfront.ordersheet.service.OrderSheetService;

/**
 * 주문서 작성 요청을 처리하는 컨트롤러
 *
 * @author 정재원
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/order-sheet")
@Slf4j
public class OrderSheetController {

    private final OrderSheetService orderSheetService;
    private final CategoryService categoryService;


    /**
     * 상품 주문시 해당 상품의 정보와 배송지 정보를 불러옵니다.
     *
     * @param productNo 개별 상품을 위한 해당 상품의 번호
     * @return 주문 작성 페이지
     */
    @GetMapping
    public String orderProduct(@RequestParam("productNo") Long productNo,
                               @RequestParam("productCnt") Integer productCnt,
                               // TODO: 2023/01/28 회원 번호 param 빼고 함수 내에서 가져올 방법 찾기
                               @RequestParam("memberNo") Long memberNo,
                               Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/categories");

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);

        OrderSheetResponseDto orderSheet =
            orderSheetService.findOrderSheetOneProduct(productNo, productCnt, memberNo);

        model.addAttribute("productDetailsList",
            orderSheet.getProductDetailsResponseDtoList());
        model.addAttribute("productCnt", productCnt);
        model.addAttribute("memberDestinationList",
            orderSheet.getMemberDestinationResponseDtoList());

        return "mainpage/ordersheet/orderSheetForm";
    }
}
