package shop.itbook.itbookfront.cart.dto.resquest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원번호를 사용하기 위한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartMemberNoRequestDto {

    private Long memberNo;
}
