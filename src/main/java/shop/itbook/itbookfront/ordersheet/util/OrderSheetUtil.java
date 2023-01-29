package shop.itbook.itbookfront.ordersheet.util;

import java.util.ArrayList;
import java.util.List;
import shop.itbook.itbookfront.order.dto.response.OrderSheetDetailResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetProductDetailResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetProductResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;

/**
 * 프론트에서 정보를 더 잘 보여주기 위해 정보를 가공하여 Dto 를 만드는 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderSheetUtil {

    public static OrderSheetProductDetailResponseDto convertProductDetailDto(
        OrderSheetProductResponseDto orderSheetProductResponseDto, Integer productCount) {

        Long discountedPrice = (long) (orderSheetProductResponseDto.getFixedPrice() * (1 -
            orderSheetProductResponseDto.getDiscountPercent() * 0.01));

        return new OrderSheetProductDetailResponseDto(orderSheetProductResponseDto.getProductName(),
            orderSheetProductResponseDto.getFixedPrice(),
            discountedPrice,
            productCount,
            discountedPrice * productCount
        );
    }

    public static OrderSheetDetailResponseDto convertOrderSheetDtoOnProduct(
        OrderSheetResponseDto orderSheetResponseDto, Integer productCount) {

        List<OrderSheetProductDetailResponseDto> orderSheetProductDetailResponseDtoList =
            new ArrayList<>();

        orderSheetProductDetailResponseDtoList.add(convertProductDetailDto(
            orderSheetResponseDto.getOrderSheetProductResponseDtoList().get(0), productCount));

        return new OrderSheetDetailResponseDto(orderSheetProductDetailResponseDtoList,
            orderSheetResponseDto.getMemberDestinationResponseDtoList());
    }
}
