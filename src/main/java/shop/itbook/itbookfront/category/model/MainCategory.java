package shop.itbook.itbookfront.category.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
/**
 * @author 최겸준
 * @since 1.0
 */
@Setter
@Getter
@NoArgsConstructor
public class MainCategory {

   private String mainCategoryName;
   private List<CategoryListResponseDto> subCategoryList;

   public MainCategory(String mainCategoryName) {
      this.mainCategoryName = mainCategoryName;
   }
}
