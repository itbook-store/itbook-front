package shop.itbook.itbookfront.category.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.category.service.CategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class CategoryAsyncController {
    private final CategoryService categoryService;

    @GetMapping("/async/{categoryNo}/sub-categories")
    public List<CategoryListResponseDto> subCategoryList(@PathVariable Integer categoryNo) {
        
        return categoryService.findCategoryList(
            "/api/admin/categories/" + categoryNo + "/child-categories");
    }
}
