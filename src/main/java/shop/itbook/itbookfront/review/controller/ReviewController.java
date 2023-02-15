package shop.itbook.itbookfront.review.controller;

import java.util.Objects;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookfront.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookfront.review.exception.ReviewAlreadyRegisteredException;
import shop.itbook.itbookfront.review.exception.ReviewNotFoundException;
import shop.itbook.itbookfront.review.service.ReviewService;

/**
 * 리뷰 컨트롤러 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/mypage/list")
    public String mypageReviewList(Model model,
                                   @PageableDefault Pageable pageable,
                                   @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        PageResponse<ReviewResponseDto> pageResponse = reviewService.findReviewListByMemberNo(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()),
            userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/review/mypage/list");

        return "mypage/review/review-mypage-list";
    }

    @GetMapping("/{memberNo}/{orderProductNo}/{productNo}/write")
    public String reviewAddForm(@PathVariable("memberNo") Long memberNo,
                                @PathVariable("orderProductNo") Long orderProductNo,
                                @PathVariable("productNo") Long productNo,
                                @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if(!memberNo.equals(userDetailsDto.getMemberNo())) {
            log.error("잘못된 접근입니다.");
            redirectAttributes.addFlashAttribute("failMessage", "잘못된 접근입니다.");
            return "redirect:/review/mypage/unwritten-list";
        }

        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setOrderProductNo(orderProductNo);
        reviewRequestDto.setProductNo(productNo);
        reviewRequestDto.setMemberNo(userDetailsDto.getMemberNo());

        model.addAttribute("reviewRequestDto", reviewRequestDto);

        return "mypage/review/review-write";
    }

    @PostMapping("/add")
    public String reviewAdd(@ModelAttribute ReviewRequestDto reviewRequestDto,
                            @RequestPart(value = "images") MultipartFile images,
                            RedirectAttributes redirectAttributes) {

        try {
            log.info("reviewRequest = {}", reviewRequestDto);
            reviewService.addReview(reviewRequestDto, images);
        } catch (ReviewAlreadyRegisteredException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/review/mypage/list";
    }

    @GetMapping("/{orderProductNo}")
    public String reviewDetails(@PathVariable("orderProductNo") Long orderProductNo,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();

        try {
            reviewResponseDto = reviewService.findReview(orderProductNo);
            log.info("reviewResponseDto = {}", reviewResponseDto);
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        model.addAttribute("reviewResponseDto", reviewResponseDto);
        return "mypage/review/review-detail";
    }

    @GetMapping("/mypage/{orderProductNo}/delete")
    public String reviewDelete(@PathVariable("orderProductNo") Long orderProductNo) {

        reviewService.deleteReview(orderProductNo);

        return "redirect:/review/mypage/list";
    }

    @GetMapping("/mypage/{orderProductNo}/modify")
    public String reviewModifyForm(@PathVariable("orderProductNo") Long orderProductNo,
                                   @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        ReviewResponseDto reviewResponseDto;

        try {
            reviewResponseDto = reviewService.findReviewForModify(
                userDetailsDto.getMemberNo(), orderProductNo);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/review/mypage/unwritten-list";
        }

        model.addAttribute("reviewResponseDto", reviewResponseDto);

        return "mypage/review/review-modify";
    }

    @PostMapping("/modify")
    public String reviewModify(@Valid @ModelAttribute ReviewRequestDto reviewRequestDto,
                               @RequestPart(value = "images") MultipartFile images,
                               RedirectAttributes redirectAttributes) {

        try {
            reviewService.modifyReview(reviewRequestDto.getOrderProductNo(), reviewRequestDto,
                images);
        } catch (ReviewAlreadyRegisteredException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/review/mypage/list";
    }

    @GetMapping("/mypage/unwritten-list")
    public String unwrittenReviewOrderProductList(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @PageableDefault Pageable pageable,
        RedirectAttributes redirectAttributes,
        Model model) {

        PageResponse<UnwrittenReviewOrderProductResponseDto> pageResponse =
            reviewService.findUnwrittenReviewOrderProductList(
                String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()),
                userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/review/mypage/list");
        model.addAttribute("memberNo", userDetailsDto.getMemberNo());

        return "mypage/review/review-writeable-list";
    }

}
