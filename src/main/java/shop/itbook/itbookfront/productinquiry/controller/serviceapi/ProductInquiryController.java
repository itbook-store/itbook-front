package shop.itbook.itbookfront.productinquiry.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/product-inquiries")
@RequiredArgsConstructor
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;

    @GetMapping("/{productNo}/add")
    public String productInquiryAddForm(
        @PathVariable("productNo")Long productNo,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        Model model) {

        ProductInquiryRequestDto productInquiryRequestDto = new ProductInquiryRequestDto();
        productInquiryRequestDto.setMemberNo(userDetailsDto.getMemberNo());
        productInquiryRequestDto.setProductNo(productNo);

        model.addAttribute("productInquiryRequestDto", productInquiryRequestDto);

        return "mainpage/productinquiry/productInquiry-write";
    }

    @PostMapping("/add")
    public String productInquiryAdd(
        @Valid @ModelAttribute ProductInquiryRequestDto productInquiryRequestDto) {

        productInquiryService.addProductInquiry(productInquiryRequestDto);

        return "redirect:/products/"+productInquiryRequestDto.getProductNo();
    }
}
