package shop.itbook.itbookfront.productinquiryreply.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookfront.productinquiryreply.service.ProductInquiryReplyService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/product-inquiries/reply")
@RequiredArgsConstructor
public class ProductInquiryReplyAdminController {

    private final ProductInquiryReplyService productInquiryReplyService;

    @PostMapping("/add")
    public String productInquiryReplyAdd(
        @Valid @ModelAttribute ProductInquiryReplyRequestDto productInquiryReplyRequestDto,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        productInquiryReplyRequestDto.setMemberNo(userDetailsDto.getMemberNo());
        productInquiryReplyService.addProductInquiryReply(productInquiryReplyRequestDto);

        return "redirect:/admin/product-inquiries/list";
    }
}
