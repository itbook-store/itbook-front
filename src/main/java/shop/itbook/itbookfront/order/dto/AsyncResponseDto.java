package shop.itbook.itbookfront.order.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsyncResponseDto<T> {
    private Boolean isSuccessful;
    private T result;
    private String message;
}
