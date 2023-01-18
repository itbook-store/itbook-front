package shop.itbook.itbookfront.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 이하늬
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class TokenRequestDto {
    private String ternantId;
    private PasswordCredentials passwordCredentials;
}
