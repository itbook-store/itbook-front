package shop.itbook.itbookfront.category.controller.serviceapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/async/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private static final String DIRECTORY_NAME = "category";

    @GetMapping
    public List<MainCategory> categoryList() {

        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList("/api/categories");

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

//        model.addAttribute("mainCategoryList", mainCategoryList);
//        return Strings.concat(DIRECTORY_NAME, "/categoryList");
        return mainCategoryList;
    }
}
