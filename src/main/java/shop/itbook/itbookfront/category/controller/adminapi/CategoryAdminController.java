package shop.itbook.itbookfront.category.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final String DIRECTORY_NAME = "adminpage/categoryadmin";

    @PostMapping("/category-addition")
    public String categoryAdd(@ModelAttribute CategoryRequestDto categoryRequestDto) {

        categoryService.addCategory(categoryRequestDto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryNo}/category-modification")
    public String categoryModifyForm(@PathVariable Integer categoryNo,
                                     @RequestParam String categoryName,
                                     @RequestParam Boolean isHidden,
                                     Model model) {

        model.addAttribute("categoryNo", categoryNo);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("isHidden", isHidden);
        return Strings.concat(DIRECTORY_NAME, "/categoryModifyForm");
    }

    @PostMapping("/{categoryNo}/category-modify")
    public String categoryModify(@PathVariable Integer categoryNo, @Valid @ModelAttribute
    CategoryModifyRequestDto categoryRequestDto) {

        categoryService.modifyCategory(categoryNo, categoryRequestDto);
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
    public String categoryModifyHidden(@PathVariable String categoryNo) {

        categoryService.modifyCategoryHidden(categoryNo);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryNo}/modify-form/main-category-sequence")
    public String mainCategorySequenceModifyForm(@PathVariable Integer categoryNo,
                                                 @RequestParam String categoryName, Model model) {

        List<CategoryListResponseDto> mainCategoryList =
            categoryService.findCategoryList("/api/admin/categories/main-categories");

        model.addAttribute("targetCategoryNo", categoryNo);
        model.addAttribute("targetCategoryName", categoryName);
        model.addAttribute("mainCategoryList", mainCategoryList);
        return Strings.concat(DIRECTORY_NAME, "/mainCategorySequenceModifyForm");
    }

    @GetMapping("/{categoryNo}/category-modify/main-category-sequence")
    public String mainCategorySequenceModify(@PathVariable Integer categoryNo,
                                             @RequestParam Integer sequence) {

        categoryService.modifyMainCategorySequence(categoryNo, sequence);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/{categoryNo}/modify-form/sub-category-sequence")
    public String subCategorySequenceModifyForm(@PathVariable Integer categoryNo,
                                                @RequestParam String categoryName, Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/admin/categories");

        model.addAttribute("targetCategoryNo", categoryNo);
        model.addAttribute("targetCategoryName", categoryName);
        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/subCategorySequenceModifyForm");
    }

    @GetMapping("/{categoryNo}/category-modify/sub-category-sequence")
    public String subCategorySequenceModify(@PathVariable Integer categoryNo,
                                            @RequestParam Integer hopingPositionCategoryNo) {

        categoryService.modifySubCategorySequence(categoryNo, hopingPositionCategoryNo);
        return "redirect:/admin/categories/";
    }


    @GetMapping
    public String categoryList(Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/admin/categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/categoryList");
    }

    @GetMapping("/main-categories")
    public String mainCategoryList(Model model) {

        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryList("/api/admin/categories/main-categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/mainCategoryList");
    }

    @GetMapping("/{categoryNo}/sub-categories")
    public String subCategoryList(@PathVariable Integer categoryNo, Model model) {

        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList(
            "/api/admin/categories/" + categoryNo + "/child-categories");

        model.addAttribute("categoryList", categoryList);
        return Strings.concat(DIRECTORY_NAME, "/subCategoryList");
    }
}
