package shop.itbook.itbookfront.product.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;
import static shop.itbook.itbookfront.home.HomeController.SIZE_OF_ALL_CONTENT;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
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
    @Cacheable(value = "personalRecommendationList", key = "#memberNo")
    public List<ProductDetailsResponseDto> getPersonalRecommendationList(Long memberNo) {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=4&memberNo=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT, memberNo)).getContent();
    }

    @Override
    @Cacheable(value = "productTypeList", key = "#productTypeNo")
    public List<ProductDetailsResponseDto> getProductTypeList(Integer productTypeNo,
                                                              Optional<UserDetailsDto> member) {
        switch (productTypeNo) {
            case 1:
                return this.getNewBookList();
            case 2:
                return this.getDiscountBookList();
            case 3:
                return this.getBestSellerList();
            case 4:
                if (member.isEmpty()) {
                    return this.getRecommendationList();
                } else {
                    return this.getPersonalRecommendationList(member.get().getMemberNo());
                }
            case 5:
                return this.getPopularBookList();
            default:
                return null;
        }
    }

    public List<ProductDetailsResponseDto> getNewBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=1", PAGE_OF_ALL_CONTENT,
                6)).getContent();
    }

    public List<ProductDetailsResponseDto> getDiscountBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=2",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

    public List<ProductDetailsResponseDto> getBestSellerList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=3",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

    public List<ProductDetailsResponseDto> getRecommendationList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=4",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT)).getContent();
    }

    public List<ProductDetailsResponseDto> getPopularBookList() {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=5",
                PAGE_OF_ALL_CONTENT, 6)).getContent();
    }

}
