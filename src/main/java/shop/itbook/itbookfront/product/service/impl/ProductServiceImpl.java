package shop.itbook.itbookfront.product.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
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
                                        ProductBookRequestDto requestDto) {
        return productAdaptor.addProduct(thumbnails, ebook, requestDto);
    }

    @Override
    public List<BookDetailsResponseDto> getBookList() {
        return productAdaptor.findBookList();
    }

    @Override
    public List<ProductDetailsResponseDto> getProductList() {
        return productAdaptor.findProductList();
    }

    @Override
    public void removeProduct(Long productNo) {
        productAdaptor.removeProduct(productNo);
    }

    @Override
    public BookDetailsResponseDto getBook(Long productNo) {
        return productAdaptor.findBook(productNo);
    }

    @Override
    public void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                              ProductBookRequestDto requestDto) {
        productAdaptor.modifyProduct(productNo, thumbnails, ebook, requestDto);
    }

    @Override
    public ProductDetailsResponseDto getProduct(Long productNo) {
        return productAdaptor.findProduct(productNo);
    }

    @Override
    public List<CategoryDetailsResponseDto> getCategoryListFilteredByProductNo(Long productNo) {
        return productAdaptor.findCategoryListWithProductNo(productNo);
    }

    @Override
    public List<ProductDetailsResponseDto> getProductListFilteredByCategoryNo(Integer categoryNo) {
        return productAdaptor.findProductListWithCategoryNo(categoryNo);
    }
}
