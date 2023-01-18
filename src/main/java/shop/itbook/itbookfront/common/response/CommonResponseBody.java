package shop.itbook.itbookfront.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 정재원
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResponseBody<T> {

    private CommonHeader header;
    
    private T result;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommonHeader {

        private String resultMessage;
    }
}
