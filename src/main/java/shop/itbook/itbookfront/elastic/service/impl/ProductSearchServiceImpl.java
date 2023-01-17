package shop.itbook.itbookfront.elastic.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.elastic.adaptor.impl.ProductSearchAdaptor;
import shop.itbook.itbookfront.elastic.dto.response.ProductSampleResponseDto;
import shop.itbook.itbookfront.elastic.service.ProductSearchService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchAdaptor productSearchAdaptor;

    public List<ProductSampleResponseDto> findProductList(String url) {
        return productSearchAdaptor.findProductList(url);
    }
}
