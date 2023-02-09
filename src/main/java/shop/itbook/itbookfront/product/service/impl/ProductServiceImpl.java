package shop.itbook.itbookfront.product.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.BookRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRequestDto;
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
    private final Integer MAIN_EXPOSED_LIMIT_NUM = 6;

    @Override
    public Long addBook(MultipartFile thumbnails, MultipartFile ebook,
                        BookRequestDto requestDto) {
        return productAdaptor.addBook(thumbnails, ebook, requestDto);
    }

    @Override
    public Long addProduct(MultipartFile thumbnails,
                           ProductRequestDto requestDto) {
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
    public void removeProduct(Long productNo) {
        productAdaptor.removeProduct(productNo);
    }

    @Override
    public void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                              BookRequestDto requestDto) {
        productAdaptor.modifyProduct(productNo, thumbnails, ebook, requestDto);
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
    public SearchBookDetailsDto searchBook(String url) {
        return productAdaptor.searchBook(url);
    }

    @Override
    public ProductBooleanResponseDto checkIsbnExists(String url) {

        return productAdaptor.isbnExists(url);
    }

    @Override
    @Cacheable(value = "newBooks")
    public List<ProductDetailsResponseDto> getNewBooks() {
        return this.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, 1)).getContent();
    }

    @Override
    @Cacheable(value = "discountBooks")
    public List<ProductDetailsResponseDto> getdiscountBooks() {
        return this.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, 2)).getContent();
    }

    @Override
    @Cacheable(value = "bestSeller")
    public List<ProductDetailsResponseDto> getBestSellers() {
        return this.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, 3)).getContent();
    }

    @Override
    @Cacheable(value = "recommendations")
    public List<ProductDetailsResponseDto> getRecommendations() {
        return this.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, 4)).getContent();
    }

    @Override
    @Cacheable(value = "popularBooks")
    public List<ProductDetailsResponseDto> getPopularBooks() {
        return this.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, 5)).getContent();
    }

}
