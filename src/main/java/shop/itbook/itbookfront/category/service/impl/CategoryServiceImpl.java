package shop.itbook.itbookfront.category.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.category.adaptor.impl.CategoryAdaptor;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.common.exception.RestApiServerException;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdaptor categoryAdaptor;

    @Override
    public void addCategory(CategoryRequestDto categoryRequestDto) {
        Integer categoryNo = categoryAdaptor.addCategory(categoryRequestDto);

        if (Objects.isNull(categoryNo)) {
            throw new RestApiServerException("해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
    }

    @Override
    public List<CategoryListResponseDto> findCategoryList(String url) {
        return categoryAdaptor.findCategoryList(url);
    }

    @Override
    public void deleteCategory(String categoryNo) {
        categoryAdaptor.deleteCategory(categoryNo);
    }

    @Override
    public void modifyCategoryHidden(String categoryNo) {
        categoryAdaptor.modifyCategoryHidden(categoryNo);
    }

}
