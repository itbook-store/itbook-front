package shop.itbook.itbookfront.category.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.category.adaptor.impl.CategoryAdaptor;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdaptor categoryAdaptor;

    public void addCategory(CategoryRequestDto categoryRequestDto) {
        categoryAdaptor.addCategory(categoryRequestDto);
    }

    public List<CategoryListResponseDto> findCategoryList(String url) {
        return categoryAdaptor.findCategoryList(url);
    }
}
