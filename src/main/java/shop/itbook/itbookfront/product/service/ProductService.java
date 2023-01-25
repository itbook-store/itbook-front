package shop.itbook.itbookfront.product.service;

import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {
    ProductNoResponseDto addBook(MultipartFile thumbnails, MultipartFile ebook,
                                 AddProductBookRequestDto requestDto);

    List<GetBookResponseDto> getBookList() throws IOException;

    GetBookResponseDto getBook(Long id);
}
