package shop.itbook.itbookfront.productinquiryreply.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookfront.productinquiryreply.adaptor.ProductInquiryReplyAdaptor;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookfront.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookfront.productinquiryreply.service.ProductInquiryReplyService;

/**
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductInquiryReplyServiceImpl implements ProductInquiryReplyService {

    private final ProductInquiryReplyAdaptor productInquiryReplyAdaptor;

    @Override
    public Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto) {

        return productInquiryReplyAdaptor.addProductInquiryReply(requestDto);
    }

    @Override
    public List<ProductInquiryReplyResponseDto> findProductInquiryReplyList(Long productInquiryNo) {

        return productInquiryReplyAdaptor.findProductInquiryReplyList(productInquiryNo);
    }
}
