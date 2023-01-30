package shop.itbook.itbookfront.product.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {
    ProductNoResponseDto addBook(MultipartFile thumbnails, MultipartFile ebook,
                                 ProductBookRequestDto requestDto);

    List<BookDetailsResponseDto> getBookList(boolean isFiltered) throws IOException;

    List<ProductDetailsResponseDto> getProductList(boolean isFiltered);

    void removeProduct(Long productNo);

    BookDetailsResponseDto getBook(Long id);

    void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                       ProductBookRequestDto requestDto);

    ProductDetailsResponseDto getProduct(Long productNo);


    List<CategoryDetailsResponseDto> getCategoryListFilteredByProductNo(Long productNo);

    List<ProductDetailsResponseDto> getProductListFilteredByCategoryNo(Integer categoryNo);
}
