package shop.itbook.itbookfront.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품을 등록한 후 Pk 값인 상품 번호를 반환하기 위한 ResponseDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductNoResponseDto {

    private Long productNo;

}
