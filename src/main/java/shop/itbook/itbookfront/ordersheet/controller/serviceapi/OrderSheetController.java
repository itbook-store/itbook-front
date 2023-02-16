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
    private final OrderSheetService orderSheetService;

    /**
     * 상품 주문시 해당 상품의 정보와 회원일 경우 배송지 정보를 불러옵니다.
     *
     * @param orderSheetFormDto 주문 요청할 상품들의 정보
     * @return 주문 작성 페이지
     * @author 정재원 *
     */
    @PostMapping
    public String orderSheet(
        @ModelAttribute("orderSheetFormDto") OrderSheetFormDto orderSheetFormDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto, Model model) {

        getOrderSheetResponseDto(orderSheetFormDto, userDetailsDto, model);

        return "mainpage/ordersheet/orderSheetForm";
    }

    /**
     * 회원의 상품 주문시 해당 상품의 정보와 배송지 정보를 불러옵니다.
     *
     * @param subscriptionPeriod 구독기간
     * @param orderSheetFormDto  주문 요청할 상품들의 정보
     * @return 주문 작성 페이지
     * @author 정재원 *
     */
    @PostMapping("/subscription")
    @SuppressWarnings("java:S5411")
    public String orderSheetSubscription(
        @RequestParam(value = "subscriptionPeriod") Integer subscriptionPeriod,
        @ModelAttribute("orderSheetFormDto") OrderSheetFormDto orderSheetFormDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto, Model model) {

        OrderSheetResponseDto orderSheetDto =
            getOrderSheetResponseDto(orderSheetFormDto, userDetailsDto, model);

        if (!orderSheetDto.getProductDetailsResponseDtoList().get(0).getIsSubscription()) {
            throw new InvalidOrderException();
        }

        List<ProductDetailsResponseDto> productDetailsResponseDtoList =
            orderSheetDto.getProductDetailsResponseDtoList();

        productDetailsResponseDtoList.get(0).setProductName(
            productDetailsResponseDtoList.get(0).getProductName() + " " + subscriptionPeriod +
                " 개월");

        productDetailsResponseDtoList.get(0).setFixedPrice(
            productDetailsResponseDtoList.get(0).getFixedPrice() * subscriptionPeriod);

        productDetailsResponseDtoList.get(0).setSelledPrice(
            productDetailsResponseDtoList.get(0).getSelledPrice() * subscriptionPeriod);

        orderSheetFormDto.setIsSubscription(Boolean.TRUE);
        orderSheetFormDto.setSubscriptionPeriod(subscriptionPeriod);

        return "mainpage/ordersheet/orderSheetForm";
    }

    /**
     * 일반 상품과 구독 상품의 주문서 공통 로직을 처리합니다.
     *
     * @param orderSheetFormDto 주문할 상품의 번호와 개수를 담은 Dto.
     * @param userDetailsDto    회원 로그인 정보
     * @return 주문서 작성에 필요한 정보가 담긴 Dto
     */
    private OrderSheetResponseDto getOrderSheetResponseDto(OrderSheetFormDto orderSheetFormDto,
                                                           UserDetailsDto userDetailsDto,
                                                           Model model) {

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        OrderSheetResponseDto orderSheetResponseDto =
            orderSheetService.findOrderSheetCartProducts(orderSheetFormDto.getProductNoList(),
                orderSheetFormDto.getProductCntList(), memberNo);

        Queue<Integer> productCntQueue = new LinkedList<>(orderSheetFormDto.getProductCntList());

        model.addAttribute("productDetailsList",
            orderSheetResponseDto.getProductDetailsResponseDtoList());
        model.addAttribute("productCntQueue", productCntQueue);
        model.addAttribute("memberDestinationList",
            orderSheetResponseDto.getMemberDestinationResponseDtoList());
        model.addAttribute("myPoint", orderSheetResponseDto.getMemberPoint());

        return orderSheetResponseDto;
    }

    /**
     * 상품 구독 시 기간을 선택합니다.
     *
     * @param productNo  구독 상품 번호
     * @param productCnt 구매할 상품 개수
     * @param model      View 를 구성할 객체
     * @return 구독 기간 선택 페이지
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
