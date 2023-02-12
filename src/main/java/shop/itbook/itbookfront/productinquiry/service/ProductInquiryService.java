package shop.itbook.itbookfront.productinquiry.service;

import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryService {

    PageResponse<ProductInquiryResponseDto> findProductInquiryList(String url);

    Long addProductInquiry(ProductInquiryRequestDto productInquiryRequestDto);
}
