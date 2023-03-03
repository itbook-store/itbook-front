package shop.itbook.itbookfront.productinquiry.controller.serviceapi;

import java.util.List;
import java.util.Objects;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookfront.productinquiry.service.ProductInquiryService;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
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

    private final MemberService memberService;

    private final MemberAdminService memberAdminService;

    @GetMapping("/{memberNo}/{productNo}/add")
    public String productInquiryAddForm(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("productNo") Long productNo,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @PageableDefault Pageable pageable,
        RedirectAttributes redirectAttributes,
        Model model) {

        if(!memberNo.equals(userDetailsDto.getMemberNo())) {
            log.error("잘못된 접근입니다.");
            redirectAttributes.addFlashAttribute("failMessage", "잘못된 접근입니다.");
            return "redirect:/product-inquiries/writable/list";
        }

        PageResponse<ProductInquiryOrderProductResponseDto> orderProductList =
            productInquiryService.findProductInquiryOrderProductList(
                String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()),
                userDetailsDto.getMemberNo());

        boolean isPurchased = false;
        for(ProductInquiryOrderProductResponseDto orderProduct : orderProductList.getContent()) {

            if(Objects.equals(orderProduct.getProductNo(), productNo)) {
                isPurchased = true;
                break;
            }
        }

        if(orderProductList.getContent().isEmpty() || !isPurchased) {
            log.error("책을 구매한 회원만 문의를 작성할 수 있습니다.");
            redirectAttributes.addFlashAttribute("failMessage", "책을 구매한 회원만 문의를 작성할 수 있습니다.");
            return "redirect:/products/" + productNo;
        }

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

        return "redirect:/product-inquiries/mypage/list";
    }

    @GetMapping("/{productInquiryNo}/delete")
    public String productInquiryDelete(@PathVariable("productInquiryNo") Long productInquiryNo,
                                       RedirectAttributes redirectAttributes) {
        /*try {
            productInquiryService.deleteProductInquiry(productInquiryNo);
        } catch (BadRequestException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }*/

        productInquiryService.deleteProductInquiry(productInquiryNo);

        return "redirect:/product-inquiries/mypage/list";
    }

    @GetMapping("/{memberNo}/{productNo}/{productInquiryNo}/modify")
    public String productInquiryModifyForm(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("productNo") Long productNo,
        @PathVariable("productInquiryNo") Long productInquiryNo,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @PageableDefault Pageable pageable,
        RedirectAttributes redirectAttributes,
        Model model) {

        if(!memberNo.equals(userDetailsDto.getMemberNo())) {
            log.error("잘못된 접근입니다.");
            redirectAttributes.addFlashAttribute("failMessage", "잘못된 접근입니다.");
            return "redirect:/product-inquiries/mypage/list";
        }

        ProductInquiryResponseDto productInquiryResponseDto;

        try {
            productInquiryResponseDto = productInquiryService.findProductInquiryInProductDetails(memberNo, productInquiryNo);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/product-inquiries/mypage/list";
        }

        model.addAttribute("productInquiryResponseDto", productInquiryResponseDto);

        return "mypage/productinquiry/productInquiry-modify";
    }

    @PostMapping("{productInquiryNo}/modify")
    public String productInquiryModify(@Valid @ModelAttribute ProductInquiryRequestDto productInquiryRequestDto,
                                       @PathVariable("productInquiryNo") Long productInquiryNo,
                                       RedirectAttributes redirectAttributes) {

        try {
            productInquiryService.modifyProductInquiry(productInquiryNo, productInquiryRequestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/product-inquiries/mypage/list";
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

        model.addAttribute("memberNo", userDetailsDto.getMemberNo());
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/product-inquiries/writable/list");

        return "mypage/productinquiry/productInquiry-writable-list";
    }

    @GetMapping("/mypage/list")
    public String productInquiryListByMemberNo(@PageableDefault Pageable pageable,
                                               @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                               Model model) {

        PageResponse<ProductInquiryResponseDto> pageResponse = productInquiryService.findProductInquiryListByMemberNo(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()), userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/product-inquiries/mypage/list");

        return "mypage/productinquiry/productInquiry-list";
    }

    @GetMapping("/view/{productInquiryNo}")
    public String productInquiryDetails(
        @PathVariable("productInquiryNo") Long productInquiryNo,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        RedirectAttributes redirectAttributes,
        Model model) {

        try {
            ProductInquiryResponseDto productInquiryResponseDto;

            if (Objects.nonNull(userDetailsDto)) {
                productInquiryResponseDto = productInquiryService.findProductInquiryInProductDetails(
                    userDetailsDto.getMemberNo(), productInquiryNo);
            } else {
                productInquiryResponseDto = productInquiryService.findProductInquiryInProductDetails(
                    -1L, productInquiryNo);
            }

            model.addAttribute("productInquiryResponseDto", productInquiryResponseDto);

            List<ProductInquiryReplyResponseDto> productInquiryReplyResponseDtoList = productInquiryReplyService.findProductInquiryReplyList(productInquiryNo);
            model.addAttribute("productInquiryReplyResponseDtoList", productInquiryReplyResponseDtoList);

        } catch (BadRequestException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("badRequestMessage", e.getMessage());
            return "redirect:/";
        }

        return "mypage/productinquiry/productInquiry-details";
    }

    @GetMapping("/view/writer/{productInquiryNo}")
    public String productInquiryDetailsForWriter(@PathVariable("productInquiryNo") Long productInquiryNo,
                                                 Model model,
                                                 RedirectAttributes redirectAttributes,
                                                 @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        MemberInfoResponseDto memberInfoResponseDto = memberService.findMember(userDetailsDto.getMemberNo());

        List<MemberRoleResponseDto> memberRoles = memberAdminService.findMemberRoles(
            userDetailsDto.getMemberNo());

        boolean hasAdmin = false;

        for (MemberRoleResponseDto memberRoleResponseDto : memberRoles) {
            if (memberRoleResponseDto.getRole().equals("ADMIN")) {
                hasAdmin = true;
                break;
            }
        }

        if(!hasAdmin && Boolean.FALSE.equals(memberInfoResponseDto.getIsWriter())) {
            log.error("작가 계정이나 관리자만 답변을 달 수 있습니다.");
            redirectAttributes.addFlashAttribute("productInquiryReplyFail", "작가 계정이나 관리자만 답변을 달 수 있습니다.");
            return "redirect:/";
        }

        ProductInquiryResponseDto productInquiryResponseDto = productInquiryService.findProductInquiry(productInquiryNo);

        model.addAttribute("productInquiryResponseDto", productInquiryResponseDto);
        model.addAttribute("productInquiryReplyRequestDto", new ProductInquiryReplyRequestDto());

        return "mypage/productinquiry/productInquiry-details-writer";
    }

}
