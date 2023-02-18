package shop.itbook.itbookfront.productinquiry.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.adaptor.ProductInquiryAdaptor;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
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

    @Override
    public void deleteProductInquiry(Long productInquiryNo) {

        productInquiryAdaptor.deleteProductInquiry(productInquiryNo);
    }

    @Override
    public void modifyProductInquiry(Long productInquiryNo,
                                     ProductInquiryRequestDto productInquiryRequestDto) {

        productInquiryAdaptor.modifyProductInquiry(productInquiryNo, productInquiryRequestDto);
    }

    @Override
    public ProductInquiryCountResponseDto countProductInquiry() {

        return productInquiryAdaptor.countProductInquiry();
    }

    @Override
    public PageResponse<ProductInquiryOrderProductResponseDto> findProductInquiryOrderProductList(
        String url, Long memberNo) {

        return productInquiryAdaptor.findProductInquiryOrderProductList(url, memberNo);
    }

    @Override
    public ProductInquiryResponseDto findProductInquiry(Long productInquiryNo) {

        return productInquiryAdaptor.findProductInquiry(productInquiryNo);
    }

    @Override
    public ProductInquiryResponseDto findProductInquiryInProductDetails(Long memberNo,
                                                                        Long productInquiryNo) {

        return productInquiryAdaptor.findProductInquiryInProductDetails(memberNo, productInquiryNo);
    }

    @Override
    public PageResponse<ProductInquiryResponseDto> findProductInquiryListByMemberNo(String url,
                                                                                    Long memberNo) {

        return productInquiryAdaptor.findProductInquiryListByMemberNo(url, memberNo);
    }

    @Override
    public PageResponse<ProductInquiryResponseDto> findProductInquiryListByProductNo(String url,
                                                                                     Long productNo) {

        return productInquiryAdaptor.findProductInquiryListByProductNo(url, productNo);
    }
}
