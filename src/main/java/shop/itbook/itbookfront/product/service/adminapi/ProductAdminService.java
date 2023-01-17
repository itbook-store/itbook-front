package shop.itbook.itbookfront.product.service.adminapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookListResponseDto;
import shop.itbook.itbookfront.product.service.FileService;

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
    private final FileService fileService;
    @Value("${object.storage.folder-path.thumbnail}")
    private String folderPathThumbnail;

    @Value("${object.storage.folder-path.ebook}")
    private String folderPathEbook;

    @Transactional
    public AddProductResponseDto addBook(
        AddProductBookRequestDto requestDto, MultipartFile thumbnails, MultipartFile ebook) {

        String thumbnailUrl =
            fileService.uploadFile(thumbnails, folderPathThumbnail);
        requestDto.setFileThumbnailsUrl(thumbnailUrl);
        if (!Objects.isNull(ebook)) {
            String ebookUrl =
                fileService.uploadFile(ebook, folderPathEbook);
            requestDto.setFileEbookUrl(ebookUrl);
        }

        return productAdaptor.addBook(requestDto);
    }

    public List<GetBookListResponseDto> getBookList() {
        List<GetBookListResponseDto> bookList = productAdaptor.getBookList();
        for (GetBookListResponseDto book : bookList) {
            fileService.download(book.getFileThumbnailsUrl());
        }
        return bookList;
    }
}
