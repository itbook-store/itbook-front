package shop.itbook.itbookfront.product.service;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.BookDetailsResponseDto;
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
                                 ProductBookRequestDto requestDto);

    PageResponse<ProductDetailsResponseDto> getProductList(String url);

    void removeProduct(Long productNo);

    void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                       ProductBookRequestDto requestDto);

    ProductDetailsResponseDto getProduct(Long productNo);


    PageResponse<CategoryDetailsResponseDto> getCategoryList(String url);

    PageResponse<ProductTypeResponseDto> findProductTypeList(String url);

    SearchBookDetailsDto searchBook(String url);


    ProductBooleanResponseDto checkIsbnExists(String url);

}
