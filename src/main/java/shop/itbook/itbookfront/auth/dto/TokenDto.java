package shop.itbook.itbookfront.auth.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto implements Serializable {

    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpirationTime;
    private Date refreshTokenExpirationTime;

}
