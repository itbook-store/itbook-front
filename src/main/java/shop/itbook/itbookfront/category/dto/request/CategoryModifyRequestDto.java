package shop.itbook.itbookfront.category.dto.request;

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
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModifyRequestDto {
    private String categoryName;
    private Boolean isHidden;
    private Integer sequence;
}
