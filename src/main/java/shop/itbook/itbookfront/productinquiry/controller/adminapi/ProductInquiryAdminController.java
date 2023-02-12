package shop.itbook.itbookfront.productinquiry.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/admin/product-inquiries")
@RequiredArgsConstructor
public class ProductInquiryAdminController {

    public final ProductInquiryService productInquiryService;

    @GetMapping("/list")
    public String productInquiryList(@PageableDefault Pageable pageable,
                                     Model model) {

        PageResponse<ProductInquiryResponseDto> pageResponse = productInquiryService.findProductInquiryList(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/product-inquiries/list");

        ProductInquiryCountResponseDto productInquiryCountResponseDto = productInquiryService.countProductInquiry();
        model.addAttribute("productInquiryCountResponseDto", productInquiryCountResponseDto);

        return "adminpage/productinquiry/productInquiry-list";
    }
}
