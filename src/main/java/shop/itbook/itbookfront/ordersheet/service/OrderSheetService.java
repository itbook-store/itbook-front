package shop.itbook.itbookfront.ordersheet.service;

import shop.itbook.itbookfront.order.dto.response.OrderSheetDetailResponseDto;

/**
 * 주문서 작성 로직을 처리하는 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderSheetService {

    /**
     * 한 상품의 주문을 위해서 정보를 가져옵니다.
     *
     * @param productNo  주문 상품의 번호
     * @param productCnt 주문 상품 갯수
     * @param memberNo   the member no
     * @return 백 서버로부터 받아온 주문서의 정보
     */
    OrderSheetDetailResponseDto findOrderSheetOneProduct(Long productNo, Integer productCnt,
                                                         Long memberNo);
}
