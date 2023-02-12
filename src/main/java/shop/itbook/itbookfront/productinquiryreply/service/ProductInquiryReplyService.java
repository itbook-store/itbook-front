package shop.itbook.itbookfront.productinquiryreply.service;

import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryReplyService {

    Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto);
}
