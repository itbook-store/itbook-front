package shop.itbook.itbookfront.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 조회중 부모카테고리의 정보까지 모두 가지고 있는 Dto클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryDetailsResponseDto {

    private Integer categoryNo;

    private String categoryName;

    private Boolean isHidden;

    private Integer level;

    private Integer parentCategoryNo;

    private String parentCategoryName;

    private Boolean parentCategoryIsHidden;

    private Integer parentCategoryLevel;

}
