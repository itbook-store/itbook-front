package shop.itbook.itbookfront.ordersheet.controller.serviceapi;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * 회원의 상품 주문시 해당 상품의 정보와 배송지 정보를 불러옵니다.
     *
     * @param productNoList      주문하려는 제품의 번호 리스트
     * @param productCntList     주문하려는 제품의 개수 리스트
     * @param orderAddRequestDto 주문하기 위해 주문서에서 작성해야할 값들이 있는 Dto
     * @return 주문 작성 페이지
     */
    @GetMapping
    public String orderProductMember(@RequestParam("productNoList") List<Long> productNoList,
                                     @RequestParam("productCntList") List<Integer> productCntList,
                                     @ModelAttribute("orderAddRequestDto")
                                     OrderAddRequestDto orderAddRequestDto,
                                     @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                     Model model) {

        // TODO: 2023/02/08 포인트 받아오기
        // TODO: 2023/02/08 사용가능한 쿠폰 받아오기
        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/categories").getContent();

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        OrderSheetResponseDto orderSheet =
            orderSheetService.findOrderSheetCartProducts(productNoList, productCntList,
                memberNo);

        Queue<Integer> productCntQueue = new LinkedList<>(productCntList);

        model.addAttribute("productDetailsList",
            orderSheet.getProductDetailsResponseDtoList());
        model.addAttribute("productCntQueue", productCntQueue);
        model.addAttribute("memberDestinationList",
            orderSheet.getMemberDestinationResponseDtoList());

        return "mainpage/ordersheet/orderSheetForm";
    }
}
