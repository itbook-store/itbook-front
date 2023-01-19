package shop.itbook.itbookfront.category.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
/**
 * @author 최겸준
 * @since 1.0
 */
public class CategoryUtil {

    public static List<MainCategory> getMainCategoryList(
        List<CategoryListResponseDto> categoryList) {

        List<MainCategory> mainCategoryList = new ArrayList<>();
        MainCategory mainCategory = null;
        List<CategoryListResponseDto> tmp = null;

        for (CategoryListResponseDto categoryListResponseDto : categoryList) {
            if (Objects.equals(categoryListResponseDto.getLevel(), 0)) {
                if (Objects.nonNull(mainCategory)) {
                    mainCategory.setSubCategoryList(tmp);
                }

                tmp = new ArrayList<>();
                mainCategory = new MainCategory(categoryListResponseDto.getCategoryName());
                mainCategoryList.add(mainCategory);
                continue;
            }

            tmp.add(categoryListResponseDto);
        }

        mainCategory.setSubCategoryList(tmp);
        return mainCategoryList;
    }
}
