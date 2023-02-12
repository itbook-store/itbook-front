package shop.itbook.itbookfront.productinquiry.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.adaptor.ProductInquiryAdaptor;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductInquiryServiceImpl implements ProductInquiryService {

    private final ProductInquiryAdaptor productInquiryAdaptor;

    @Override
    public PageResponse<ProductInquiryResponseDto> findProductInquiryList(String url) {

        return productInquiryAdaptor.findProductInquiryList(url);
    }

    @Override
    public Long addProductInquiry(ProductInquiryRequestDto productInquiryRequestDto) {

        return productInquiryAdaptor.addProductInquiry(productInquiryRequestDto);
    }
}
