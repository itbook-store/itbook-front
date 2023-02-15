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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.order.dto.request.OrderSheetFormDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookfront.order.exception.InvalidOrderException;
import shop.itbook.itbookfront.ordersheet.service.OrderSheetService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

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
    // TODO: 2023/02/15 구독과 일반 상품 공통 로직 리팩토링하기
    private final OrderSheetService orderSheetService;
    private final CategoryService categoryService;

    private final MemberService memberService;

    /**
     * 상품 주문시 해당 상품의 정보와 회원일 경우 배송지 정보를 불러옵니다.
     *
     * @param orderSheetFormDto 주문 요청할 상품의 정보
     * @return 주문 작성 페이지
     * @author 정재원 *
     */
    @PostMapping
    public String orderSheet(
        @ModelAttribute("orderSheetFormDto")
        OrderSheetFormDto orderSheetFormDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/categories").getContent();

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);

        Optional<Long> memberNo = Optional.empty();

        OrderSheetResponseDto orderSheetDto =
            orderSheetService.findOrderSheetCartProducts(orderSheetFormDto.getProductNoList(),
                orderSheetFormDto.getProductCntList(),
                memberNo);

        Queue<Integer> productCntQueue = new LinkedList<>(orderSheetFormDto.getProductCntList());

        model.addAttribute("productDetailsList",
            orderSheetDto.getProductDetailsResponseDtoList());
        model.addAttribute("productCntQueue", productCntQueue);
        model.addAttribute("memberDestinationList",
            orderSheetDto.getMemberDestinationResponseDtoList());
        model.addAttribute("myPoint", orderSheetDto.getMemberPoint());

        return "mainpage/ordersheet/orderSheetForm";
    }

    /**
     * 회원의 상품 주문시 해당 상품의 정보와 배송지 정보를 불러옵니다.
     *
     * @param subscriptionPeriod 구독기간
     * @param orderSheetFormDto  주문 ㅇ
     * @return 주문 작성 페이지
     * @author 정재원 *
     */
    @PostMapping("/subscription")
    @SuppressWarnings("java:S5411")
    public String orderSubscription(
        @RequestParam(value = "subscriptionPeriod")
        Integer subscriptionPeriod,
        @ModelAttribute("orderSheetFormDto")
        OrderSheetFormDto orderSheetFormDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        Model model) {

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        // 주문서 받아오기
        OrderSheetResponseDto orderSheetDto =
            orderSheetService.findOrderSheetCartProducts(orderSheetFormDto.getProductNoList(),
                orderSheetFormDto.getProductCntList(),
                memberNo);

        if (!orderSheetDto.getProductDetailsResponseDtoList().get(0).getIsSubscription()) {
            throw new InvalidOrderException();
        }

        Queue<Integer> productCntQueue = new LinkedList<>(orderSheetFormDto.getProductCntList());

        List<ProductDetailsResponseDto> productDetailsResponseDtoList =
            orderSheetDto.getProductDetailsResponseDtoList();

        productDetailsResponseDtoList.get(0)
            .setSelledPrice(
                productDetailsResponseDtoList.get(0).getFixedPrice() * subscriptionPeriod);

        productDetailsResponseDtoList.get(0)
            .setSelledPrice(
                productDetailsResponseDtoList.get(0).getSelledPrice() * subscriptionPeriod);

        model.addAttribute("productDetailsList",
            orderSheetDto.getProductDetailsResponseDtoList());
        model.addAttribute("productCntQueue", productCntQueue);
        model.addAttribute("memberDestinationList",
            orderSheetDto.getMemberDestinationResponseDtoList());

        orderSheetFormDto.setIsSubscription(Boolean.TRUE);
        orderSheetFormDto.setSubscriptionPeriod(subscriptionPeriod);
        model.addAttribute("myPoint", orderSheetDto.getMemberPoint());

        return "mainpage/ordersheet/orderSheetForm";
    }

    /**
     * Order subscription period select string.
     *
     * @param productNo  the product no
     * @param productCnt the product cnt
     * @param model      the model
     * @return the string
     * @author 정재원 *
     */
    @GetMapping("/subscription/select-period")
    public String orderSubscriptionPeriodSelect(@RequestParam("productNo") Long productNo,
                                                @RequestParam("productCnt") Integer productCnt,
                                                Model model) {

        model.addAttribute("productNo", productNo);
        model.addAttribute("productCnt", productCnt);

        return "mainpage/ordersheet/subscriptionOrderForm";
    }
}
