package shop.itbook.itbookfront.category.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;
    private static final String DIRECTORY_NAME = "categoryadmin";

    @GetMapping("/add")
    public String categoryAdd() {

        return Strings.concat(DIRECTORY_NAME, "/categoryAddForm");
    }

    @GetMapping
    public String categoryList(Model model) {

        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList("/api/admin/categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/categoryList");
    }

    @GetMapping("/main-categories")
    public String mainCategoryList(Model model) {

        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList("/api/admin/categories/main-categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/mainCategoryList");
    }

    @GetMapping("/{categoryNo}/sub-categories")
    public String subCategoryList(@PathVariable Integer categoryNo, Model model) {

        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList("/api/admin/categories/" + categoryNo + "/child-categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/subCategoryList");
    }
}
