package shop.itbook.itbookfront.ordersheet.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookfront.ordersheet.service.OrderSheetService;
import shop.itbook.itbookfront.signin.dto.request.MemberInputRequestDto;

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
                               @ModelAttribute("orderAddRequestDto")
                               OrderAddRequestDto orderAddRequestDto,
                               @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                               Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/categories").getContent();

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);

        OrderSheetResponseDto orderSheet =
            orderSheetService.findOrderSheetOneProduct(productNo, productCnt,
                userDetailsDto.getMemberNo());

        model.addAttribute("productDetailsList",
            orderSheet.getProductDetailsResponseDtoList());
        model.addAttribute("productCnt", productCnt);
        model.addAttribute("memberDestinationList",
            orderSheet.getMemberDestinationResponseDtoList());

        return "mainpage/ordersheet/orderSheetForm";
    }
}
