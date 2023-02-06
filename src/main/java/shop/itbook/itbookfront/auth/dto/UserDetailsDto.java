package shop.itbook.itbookfront.auth.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Authentication Principal 객체에 저장할 회원 정보를 담는 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto implements Serializable{
    private Long memberNo;
    private String memberId;
}
