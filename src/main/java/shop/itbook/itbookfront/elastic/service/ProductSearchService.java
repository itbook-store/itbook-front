package shop.itbook.itbookfront.elastic.service;

import java.util.List;
import shop.itbook.itbookfront.elastic.dto.response.ProductSampleResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchService {
    List<ProductSampleResponseDto> findProductList(String url);
}
