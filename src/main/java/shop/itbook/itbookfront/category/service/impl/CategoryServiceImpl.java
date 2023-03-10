package shop.itbook.itbookfront.category.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.category.adaptor.CategoryAdaptor;
import shop.itbook.itbookfront.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.common.response.PageResponse;

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
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
    }

    @Override
    public PageResponse<CategoryListResponseDto> findCategoryList(String url) {
        return categoryAdaptor.findCategoryList(url);
    }

    @Override
    @Cacheable(value = "categories")
    public List<CategoryListResponseDto> findCategoryListForUser() {
        return categoryAdaptor.findCategoryList(String.format("/api/categories?page=%d&size=%d",
            PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(String categoryNo) {
        categoryAdaptor.deleteCategory(categoryNo);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void modifyCategoryHidden(String categoryNo) {
        categoryAdaptor.modifyCategoryHidden(categoryNo);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void modifyMainCategorySequence(Integer categoryNo, Integer sequence) {
        categoryAdaptor.modifyMainCategorySequence(categoryNo, sequence);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void modifySubCategorySequence(Integer categoryNo, Integer hopingPositionCategoryNo) {
        categoryAdaptor.modifySubCategorySequence(categoryNo, hopingPositionCategoryNo);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto) {
        categoryAdaptor.modifyCategory(categoryNo, categoryRequestDto);
    }
}
