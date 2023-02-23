package shop.itbook.itbookfront.category.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryAsyncController {
    private final CategoryService categoryService;

    @GetMapping("/async/{categoryNo}/sub-categories")
    public List<CategoryListResponseDto> subCategoryList(@PathVariable Integer categoryNo) {

        try {

            return categoryService.findCategoryList(
                "/api/admin/categories/" + categoryNo + "/child-categories?page=0&size=" +
                    Integer.MAX_VALUE).getContent();
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/async/mainpage/category-bar")
    public List<MainCategory> mainPageCategoryBar(Model model) {

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryService.findCategoryListForUser());

        model.addAttribute("mainCategoryList", mainCategoryList);

        return mainCategoryList;
    }
}
