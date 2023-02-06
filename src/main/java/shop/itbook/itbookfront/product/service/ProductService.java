package shop.itbook.itbookfront.product.service;

import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.BookRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {
    ProductNoResponseDto addBook(MultipartFile thumbnails, MultipartFile ebook,
                                 BookRequestDto requestDto);

    ProductNoResponseDto addProduct(MultipartFile thumbnails, ProductRequestDto requestDto);

    PageResponse<ProductDetailsResponseDto> getProductList(String url);

    void removeProduct(Long productNo);

    void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                       BookRequestDto requestDto);

    ProductDetailsResponseDto getProduct(Long productNo);


    PageResponse<CategoryDetailsResponseDto> getCategoryList(String url);

    PageResponse<ProductTypeResponseDto> findProductTypeList(String url);

    SearchBookDetailsDto searchBook(String url);


    ProductBooleanResponseDto checkIsbnExists(String url);

}
