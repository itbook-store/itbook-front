package shop.itbook.itbookfront.category.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.exception.AlreadyAddedCategoryNameException;
import shop.itbook.itbookfront.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookfront.category.exception.ParentCategoryNotFoundException;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.exception.CategoryNumberNotFoundException;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryService categoryService;
    private static final String DIRECTORY_NAME = "adminpage/categoryadmin";

    @PostMapping("/category-addition")
    public String categoryAdd(@ModelAttribute CategoryRequestDto categoryRequestDto,
                              RedirectAttributes redirectAttributes) {

        try {
            categoryService.addCategory(categoryRequestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/category-addition";
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/category-addition/sub-category/select-form")
    public String categoryAddSubCategorySelectForm(Model model,
                                                   @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {

            pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                        pageable.getPageNumber(), pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/category-addition";
        }

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            "/admin/categories/category-addition/sub-category/select-form");
        return Strings.concat(DIRECTORY_NAME, "/mainCategorySelectForm");
    }

    @GetMapping("/category-addition/sub-category/{mainCategoryNo}")
    public String categoryAddSubCategoryForm(@PathVariable Integer mainCategoryNo, Model model) {

        model.addAttribute("mainCategoryNo", mainCategoryNo);
        return Strings.concat(DIRECTORY_NAME, "/subCategoryAddForm");
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
    CategoryModifyRequestDto categoryRequestDto, RedirectAttributes redirectAttributes) {

        try {
            categoryService.modifyCategory(categoryNo, categoryRequestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryNo}/category-modify/hidden")
    public String categoryModifyHidden(@PathVariable String categoryNo, RedirectAttributes redirectAttributes) {

        try {
            categoryService.modifyCategoryHidden(categoryNo);
        } catch (CategoryNumberNotFoundException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryNo}/modify-form/main-category-sequence")
    public String mainCategorySequenceModifyForm(@PathVariable Integer categoryNo,
                                                 @RequestParam String categoryName, Model model,
                                                 @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {
            pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                        pageable.getPageNumber(), pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/categories";
        }

        model.addAttribute("targetCategoryNo", categoryNo);
        model.addAttribute("targetCategoryName", categoryName);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            String.format("/admin/categories/%d/modify-form/main-category-sequence?categoryName=%s",
                categoryNo, categoryName));
        return Strings.concat(DIRECTORY_NAME, "/mainCategorySequenceModifyForm");
    }

    @GetMapping("/{categoryNo}/category-modify/main-category-sequence")
    public String mainCategorySequenceModify(@PathVariable Integer categoryNo,
                                             @RequestParam Integer sequence, RedirectAttributes redirectAttributes) {

        try {
            categoryService.modifyMainCategorySequence(categoryNo, sequence);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return "redirect:/admin/categories/";
    }

    @GetMapping("/{categoryNo}/modify-form/sub-category-sequence")
    public String subCategorySequenceModifyForm(@PathVariable Integer categoryNo,
                                                @RequestParam String categoryName,
                                                @PageableDefault Pageable pageable, Model model, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {
            pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/categories";
        }

        model.addAttribute("targetCategoryNo", categoryNo);
        model.addAttribute("targetCategoryName", categoryName);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            String.format("/admin/categories/%d/modify-form/sub-category-sequence?categoryName=%s",
                categoryNo, categoryName));
        return Strings.concat(DIRECTORY_NAME, "/subCategorySequenceModifyForm");
    }

    @GetMapping("/{categoryNo}/category-modify/sub-category-sequence")
    public String subCategorySequenceModify(@PathVariable Integer categoryNo,
                                            @RequestParam Integer hopingPositionCategoryNo, RedirectAttributes redirectAttributes) {

        try {
            categoryService.modifySubCategorySequence(categoryNo, hopingPositionCategoryNo);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return "redirect:/admin/categories/";
    }

    @GetMapping("/{categoryNo}/category-deletion")
    public String categoryDelete(@PathVariable String categoryNo,
                                 RedirectAttributes redirectAttributes) {

        try {
            categoryService.deleteCategory(categoryNo);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/admin/categories";
    }

    @GetMapping
    public String categoryList(Model model, @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {
            pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/categories";
        }

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/categories");
        return Strings.concat(DIRECTORY_NAME, "/categoryList");
    }

    @GetMapping("/main-categories")
    public String mainCategoryList(Model model, @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {
            pageResponse =
                categoryService.findCategoryList(
                    String.format("/api/admin/categories/main-categories?page=%d&size=%d",
                        pageable.getPageNumber(), pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/categories";
        }

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/categories/main-categories");
        return Strings.concat(DIRECTORY_NAME, "/mainCategoryList");
    }

    @GetMapping("/{categoryNo}/sub-categories")
    public String subCategoryList(@PathVariable Integer categoryNo,
                                  @RequestParam String parentCategoryName, Model model,
                                  @PageableDefault Pageable pageable, RedirectAttributes redirectAttributes) {

        PageResponse<CategoryListResponseDto> pageResponse;
        try {
            pageResponse = categoryService.findCategoryList(
                String.format("/api/admin/categories/%d/child-categories?page=%d&size=%d", categoryNo,
                    pageable.getPageNumber(), pageable.getPageSize()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/admin/categories";
        }

        model.addAttribute("parentCategoryName", parentCategoryName);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            String.format("/admin/categories/%d/sub-categories?parentCategoryName=%s", categoryNo,
                parentCategoryName));
        return Strings.concat(DIRECTORY_NAME, "/subCategoryList");
    }
}
