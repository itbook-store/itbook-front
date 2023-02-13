package shop.itbook.itbookfront.product.service;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {
    Long addProduct(MultipartFile thumbnails, ProductAddRequestDto requestDto);

    PageResponse<ProductDetailsResponseDto> getProductList(String url);

    ProductDetailsResponseDto getProduct(Long productNo);


    PageResponse<CategoryDetailsResponseDto> getCategoryList(String url);

    PageResponse<ProductTypeResponseDto> findProductTypeList(String url);

    PageResponse<ProductRelationResponseDto> findRelationProductList(String url);


    void modifyProduct(Long productNo, MultipartFile thumbnails,
                       ProductModifyRequestDto requestDto);


    void modifyRelationProduct(Long basedProductNo, ProductRelationRequestDto requestDto);


    void changeBooleanField(Long productNo, String fieldName);

    void updateDailyHits(Long productNo);

    Cookie checkCookieForDailyHits(Long productNo, HttpServletRequest request,
                                   HttpServletResponse response);

    PageResponse<ProductSalesRankResponseDto> findSalesRankProductList(
        @PageableDefault Pageable pageable,
        String sortingCriteria);
}
