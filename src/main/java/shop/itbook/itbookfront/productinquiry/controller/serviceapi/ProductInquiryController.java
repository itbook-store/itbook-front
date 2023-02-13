package shop.itbook.itbookfront.productinquiry.controller.serviceapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookfront.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookfront.productinquiryreply.service.ProductInquiryReplyService;

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
    private final ProductInquiryReplyService productInquiryReplyService;

    @GetMapping("/{productNo}/add")
    public String productInquiryAddForm(
        @PathVariable("productNo") Long productNo,
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

        return "redirect:/products/" + productInquiryRequestDto.getProductNo();
    }

    @GetMapping("/writable/list")
    public String productInquiryOrderProductList(
        @PageableDefault Pageable pageable,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        Model model) {

        PageResponse<ProductInquiryOrderProductResponseDto> pageResponse =
            productInquiryService.findProductInquiryOrderProductList(
                String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()),
                userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/product-inquiries/list");

        return "mypage/productinquiry/productInquiry-writable-list";
    }

    @GetMapping("/mypage/list")
    public String productInquiryListByMemberNo(@PageableDefault Pageable pageable,
                                               @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                               Model model) {

        PageResponse<ProductInquiryResponseDto> pageResponse = productInquiryService.findProductInquiryListByMemberNo(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()), userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/product-inquiries/list");

        return "mypage/productinquiry/productInquiry-list";
    }

    @GetMapping("/view/{productInquiryNo}")
    public String productInquiryDetails(
        @PathVariable("productInquiryNo") Long productInquiryNo,
        Model model) {

        ProductInquiryResponseDto productInquiryResponseDto = productInquiryService.findProductInquiry(productInquiryNo);
        model.addAttribute("productInquiryResponseDto", productInquiryResponseDto);

        List<ProductInquiryReplyResponseDto> productInquiryReplyResponseDtoList = productInquiryReplyService.findProductInquiryReplyList(productInquiryNo);
        model.addAttribute("productInquiryReplyResponseDtoList", productInquiryReplyResponseDtoList);

        return "mypage/productinquiry/productInquiry-details";
    }
}
