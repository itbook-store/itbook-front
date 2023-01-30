package shop.itbook.itbookfront.category.service;

import shop.itbook.itbookfront.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryService {

    void addCategory(CategoryRequestDto categoryRequestDto);

    PageResponse<CategoryListResponseDto> findCategoryList(String url);

    void deleteCategory(String categoryNo);

    void modifyCategoryHidden(String categoryNo);

    void modifyMainCategorySequence(Integer categoryNo, Integer sequence);

    void modifySubCategorySequence(Integer categoryNo, Integer hopingPositionCategoryNo);

    void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto);
}
