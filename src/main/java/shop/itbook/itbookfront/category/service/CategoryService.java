package shop.itbook.itbookfront.category.service;

import java.util.List;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryService {

    void addCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryListResponseDto> findCategoryList(String url);
}
