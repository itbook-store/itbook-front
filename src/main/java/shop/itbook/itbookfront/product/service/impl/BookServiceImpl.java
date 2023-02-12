package shop.itbook.itbookfront.product.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.BookAdaptor;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.service.BookService;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * BookService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookAdaptor bookAdaptor;
    private final ProductService productService;
    private final Integer MAIN_EXPOSED_LIMIT_NUM = 6;

    @Override
    public Long addBook(MultipartFile thumbnails, MultipartFile ebook,
                        BookAddRequestDto requestDto) {
        return bookAdaptor.addBook(thumbnails, ebook, requestDto);
    }

    @Override
    public void modifyBook(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                           BookModifyRequestDto requestDto) {
        bookAdaptor.modifyBook(productNo, thumbnails, ebook, requestDto);
    }

    @Override
    public SearchBookDetailsDto searchBook(String url) {
        return bookAdaptor.searchBook(url);
    }

    @Override
    public ProductBooleanResponseDto checkIsbnExists(String url) {

        return bookAdaptor.isbnExists(url);
    }

    @Override
    public List<ProductDetailsResponseDto> getProductTypeListByTypeNo(
        Integer productTypeNo, Long memberNo) {
        switch (productTypeNo) {
            case 1:
                return getNewBookList();
            case 2:
                return getDiscountBookList();
            case 3:
                return getBestSellerList();
            case 4:
                if (Objects.isNull(memberNo)) {
                    return getRecommendationList();
                } else {
                    return getPersonalRecommendationList(memberNo);
                }
            case 5:
                return getPopularBookList();
            default:
                return null;
        }
    }

    @Override
    @Cacheable(value = "newBookList")
    public List<ProductDetailsResponseDto> getNewBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=1", PAGE_OF_ALL_CONTENT,
                6)).getContent();
    }

    @Override
    @Cacheable(value = "discountBookList")
    public List<ProductDetailsResponseDto> getDiscountBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=2",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

    @Override
    @Cacheable(value = "bestSellerList")
    public List<ProductDetailsResponseDto> getBestSellerList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=3",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

    @Override
    @Cacheable(value = "recommendationList")
    public List<ProductDetailsResponseDto> getRecommendationList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=4",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();
    }

    @Override
//    @Cacheable(value = "personalRecommendationList")
    public List<ProductDetailsResponseDto> getPersonalRecommendationList(Long memberNo) {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=4&memberNo=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT, memberNo)).getContent();
    }

    @Override
    @Cacheable(value = "popularBookList")
    public List<ProductDetailsResponseDto> getPopularBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=5",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

}
