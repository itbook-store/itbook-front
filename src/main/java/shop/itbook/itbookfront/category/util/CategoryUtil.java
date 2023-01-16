package shop.itbook.itbookfront.category.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import shop.itbook.itbookfront.category.model.MainCategory;

/**
 * @author 최겸준
 * @since 1.0
 */
public class CategoryUtil {

    public static List<MainCategory> getMainCategoryList(
        List<shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto> categoryList) {

        List<MainCategory> mainCategoryList = new ArrayList<>();
        MainCategory mainCategory = null;
        List<shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto> tmp = null;

        for (shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto categoryListResponseDto : categoryList) {
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
