package shop.itbook.itbookfront.product.service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface BookService {
    void modifyBook(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                    BookModifyRequestDto requestDto);

    SearchBookDetailsDto searchBook(String url);


    ProductBooleanResponseDto checkIsbnExists(String url);

    Long addBook(MultipartFile thumbnails, MultipartFile ebook,
                 BookAddRequestDto requestDto);

    List<ProductDetailsResponseDto> getPersonalRecommendationList(Long memberNo);

    List<ProductDetailsResponseDto> getProductTypeList(Integer productTypeNo,
                                                       Optional<UserDetailsDto> member);
}
