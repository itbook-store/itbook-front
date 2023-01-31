package shop.itbook.itbookfront.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.adaptor.ProductAdaptor;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
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
    public PageResponse<ProductDetailsResponseDto> getProductList(String url) {
        return productAdaptor.findProductList(url);
    }

    @Override
    public PageResponse<CategoryDetailsResponseDto> getCategoryList(String url) {
        return productAdaptor.findCategoryList(url);
    }

    @Override
    public void removeProduct(Long productNo) {
        productAdaptor.removeProduct(productNo);
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

}
