package shop.itbook.itbookfront.productinquiry.service;

import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryService {

    PageResponse<ProductInquiryResponseDto> findProductInquiryList(String url);

    Long addProductInquiry(ProductInquiryRequestDto productInquiryRequestDto);

    ProductInquiryCountResponseDto countProductInquiry();

    PageResponse<ProductInquiryOrderProductResponseDto> findProductInquiryOrderProductList(String url, Long memberNo);

    ProductInquiryResponseDto findProductInquiry(Long productInquiryNo);

    ProductInquiryResponseDto findProductInquiryInProductDetails(Long memberNo, Long productInquiryNo);

    PageResponse<ProductInquiryResponseDto> findProductInquiryListByMemberNo(String url, Long memberNo);

    PageResponse<ProductInquiryResponseDto> findProductInquiryListByProductNo(String url, Long productNo);
}
