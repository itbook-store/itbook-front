package shop.itbook.itbookfront.product.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductAdaptor productAdaptor;

    @Override
    public Long addProduct(MultipartFile thumbnails,
                           ProductAddRequestDto requestDto) {
        return productAdaptor.addProduct(thumbnails, requestDto);
    }

    @Override
    public PageResponse<ProductDetailsResponseDto> getProductList(String url) {
        return productAdaptor.findProductList(url);
    }

    @Override
    public PageResponse<CategoryDetailsResponseDto> getCategoryList(String url) {
        return productAdaptor.findCategoryList(url);
    }

    @Override
    public void modifyProduct(Long productNo, MultipartFile thumbnails,
                              ProductModifyRequestDto requestDto) {
        productAdaptor.modifyProduct(productNo, thumbnails, requestDto);
    }

    @Override
    public void modifyRelationProduct(Long basedProductNo, ProductRelationRequestDto requestDto) {
        productAdaptor.modifyRelationProduct(basedProductNo, requestDto);

    }

    @Override
    public ProductDetailsResponseDto getProduct(Long productNo) {
        return productAdaptor.findProduct(productNo);
    }

    @Override
    public PageResponse<ProductTypeResponseDto> findProductTypeList(String url) {
        return productAdaptor.findProductTypeList(url);
    }

    @Override
    public PageResponse<ProductRelationResponseDto> findRelationProductList(String url) {
        return productAdaptor.findRelationProductList(url);
    }

    @Override
    public void changeBooleanField(Long productNo, String fieldName) {
        productAdaptor.changeBooleanField(productNo, fieldName);
    }

}
