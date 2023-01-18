package shop.itbook.itbookfront.category.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;

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

    @PostMapping("/category-addition")
    public String categoryAdd(@ModelAttribute CategoryRequestDto categoryRequestDto) {

        categoryService.addCategory(categoryRequestDto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/category-addition/sub-category")
    public String categoryAddSubCategory(Model model) {

         List<CategoryListResponseDto> mainCategoryList =
            categoryService.findCategoryList("/api/admin/categories/main-categories");

        model.addAttribute("mainCategoryList", mainCategoryList);
        return Strings.concat(DIRECTORY_NAME, "/subCategoryAddForm");
    }

    @GetMapping("/{categoryNo}/category-deletion")
    public String categoryDelete(@PathVariable String categoryNo) {

        categoryService.deleteCategory(categoryNo);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryNo}/category-modify/hidden")
    public String categoryModify(@PathVariable String categoryNo) {

        categoryService.modifyCategoryHidden(categoryNo);
        return "redirect:/admin/categories";
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
