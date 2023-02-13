package shop.itbook.itbookfront.productinquiryreply.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookfront.productinquiryreply.service.ProductInquiryReplyService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/product-inquiries/reply")
@RequiredArgsConstructor
public class ProductInquiryReplyController {

    private final ProductInquiryReplyService productInquiryReplyService;
    private final ProductInquiryService productInquiryService;

    @PostMapping("/add")
    public String productInquiryReplyAdd(
        @Valid @ModelAttribute ProductInquiryReplyRequestDto productInquiryReplyRequestDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        Long productNo = productInquiryService.findProductInquiry(productInquiryReplyRequestDto.getProductInquiryNo()).getProductNo();
        productInquiryReplyRequestDto.setMemberNo(userDetailsDto.getMemberNo());
        productInquiryReplyService.addProductInquiryReply(productInquiryReplyRequestDto);

        return "redirect:/products/" + productNo;
    }
}
