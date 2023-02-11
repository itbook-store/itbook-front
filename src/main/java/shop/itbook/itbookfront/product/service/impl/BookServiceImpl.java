package shop.itbook.itbookfront.product.service.impl;

import static shop.itbook.itbookfront.home.HomeController.PAGE_OF_ALL_CONTENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.BookAdaptor;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
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
    @Cacheable(value = "productTypes", key = "#id")
    public List<ProductDetailsResponseDto> getBooksByProductTypes(Integer id) {
        return productService.getProductList(
            String.format("/api/products?page=%d&size=%d&productTypeNo=%d",
                PAGE_OF_ALL_CONTENT, MAIN_EXPOSED_LIMIT_NUM, id)).getContent();
    }

}
