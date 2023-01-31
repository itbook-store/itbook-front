package shop.itbook.itbookfront.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 폼 로그인을 통해 들어온 정보를 AuthServer로 보내기 위한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
public class MemberAuthRequestDto {
    String memberId;
    String password;
}
