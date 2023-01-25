package shop.itbook.itbookfront.product.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.GetProductResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductAdaptor productAdaptor;

    @Transactional
    @Override
    public ProductNoResponseDto addBook(MultipartFile thumbnails, MultipartFile ebook,
                                        AddProductBookRequestDto requestDto) {
        return productAdaptor.addProduct(thumbnails, ebook, requestDto);
    }

    @Override
    public List<GetBookResponseDto> getBookList() {
        List<GetBookResponseDto> bookList = productAdaptor.getBookList();
        return bookList;
    }

    @Override
    public List<GetProductResponseDto> getProductList() {
        List<GetProductResponseDto> productList = productAdaptor.getProductList();
        return productList;
    }

    @Override
    public void removeProduct(Long productNo) {
        productAdaptor.removeProduct(productNo);
    }

    @Override
    public GetBookResponseDto getBook(Long id) {
        return productAdaptor.getBook(id);
    }

    @Override
    public void modifyProduct(Long productNo) {
        productAdaptor.modifyProduct(productNo);
    }
}
