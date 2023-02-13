package shop.itbook.itbookfront.productinquiryreply.service;

import java.util.List;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookfront.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryReplyService {

    Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto);

    List<ProductInquiryReplyResponseDto> findProductInquiryReplyList(Long productInquiryNo);
}
