package shop.itbook.itbookfront.product.service.adminapi;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductAdminService {

    private final ProductAdaptor productAdaptor;

    @Transactional
    public ProductNoResponseDto addBook(MultipartFile thumbnails, MultipartFile ebook,
                                        AddProductBookRequestDto requestDto) {
        return productAdaptor.addBook(thumbnails, ebook, requestDto);
    }

    public List<GetBookResponseDto> getBookList() throws IOException {
        List<GetBookResponseDto> bookList = productAdaptor.getBookList();
//        for (GetBookResponseDto book : bookList) {
//            fileService.download(book.getFileThumbnailsUrl());
//        }
        return bookList;
    }

    public GetBookResponseDto getBook(Long id) {
        return productAdaptor.getBook(id);
    }

}
