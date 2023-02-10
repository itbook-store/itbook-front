package shop.itbook.itbookfront.ordersheet.service;

import java.util.List;
import java.util.Optional;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;

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
     * @param productNoList  주문할 상품 번호들의 리스트
     * @param productCntList 주문할 상품 개수의 리스트
     * @param memberNo       주문하는 회원의 번호
     * @return 백 서버로부터 받아온 주문서의 정보
     */
    OrderSheetResponseDto findOrderSheetCartProducts(List<Long> productNoList,
                                                     List<Integer> productCntList,
                                                     Optional<Long> memberNo);
}
