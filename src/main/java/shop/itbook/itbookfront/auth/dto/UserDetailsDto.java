package shop.itbook.itbookfront.auth.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto implements Serializable {
    private Long memberNo;
    private String memberId;
}
