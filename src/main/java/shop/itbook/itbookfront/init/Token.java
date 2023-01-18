package shop.itbook.itbookfront.init;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class Token {
    private String id;
    private String expires;
}
