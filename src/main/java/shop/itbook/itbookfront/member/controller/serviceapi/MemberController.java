package shop.itbook.itbookfront.member.controller.serviceapi;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.member.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberPointSendRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRecentlyPointResponseDto;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/mypage/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me/info")
    public String mypageInfo(Model model,
                             @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        MemberInfoResponseDto memberInfoResponseDto =
            memberService.findMemberInfo(userDetailsDto.getMemberId());

        model.addAttribute("memberInfoResponseDto", memberInfoResponseDto);

        return "mypage/member/member-info-modify";
    }

    @PostMapping("/modify")
    public String mypageInfoModify(@Valid MemberUpdateRequestDto memberUpdateRequestDto,
                                   @RequestParam("memberId") String memberId) {

        memberService.updateMemberInfo(memberUpdateRequestDto, memberId);

        return "redirect:/mypage";

    }

    @GetMapping("/withdraw")
    public String memberStatusModifyToWithdraw(@ModelAttribute("memberStatusChangeRequestDto")
                                               MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                               @AuthenticationPrincipal
                                               UserDetailsDto userDetailsDto,
                                               Model model) {
        MemberInfoResponseDto member = memberService.findMemberInfo(userDetailsDto.getMemberId());
        model.addAttribute("member", member);

        return "mypage/member/member-withdraw";
    }

    @PostMapping("/{memberId}/withdraw")
    public String memberWithdraw(@PathVariable("memberId") String memberId,
                                 @Valid MemberStatusChangeRequestDto memberStatusChangeRequestDto) {

        memberService.withdrawMember(memberId, memberStatusChangeRequestDto);

        return "redirect:/logout";
    }

    @GetMapping("/me/modify/password")
    public String memberPwdModify() {
        return "mypage/member/member-password-modify";
    }

    @GetMapping("/me/destinations")
    public String memberDestinationList(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                        @ModelAttribute
                                        MemberDestinationRequestDto memberDestinationRequestDto,
                                        Model model) {

        List<MemberDestinationResponseDto> memberDestinationList =
            memberService.findMemberDestinationList(
                userDetailsDto.getMemberNo());

        model.addAttribute("memberDestinationList", memberDestinationList);
        model.addAttribute("memberNo", userDetailsDto.getMemberNo());

        return "mypage/member/member-destination-list";
    }

    @PostMapping("/me/destinations/delete")
    public String memberDestinationsDelete(@RequestParam List<Long> checkedDestinationList) {

        List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList = new ArrayList<>();

        for (Long memberDestinationNo : checkedDestinationList) {
            memberDestinationNoResponseDtoList.add(
                new MemberDestinationNoResponseDto(memberDestinationNo));
        }

        memberService.deleteMemberDestinations(memberDestinationNoResponseDtoList);

        return "redirect:/mypage/members/me/destinations";
    }

    @PostMapping("/me/destinations/add")
    public String memberDestinationAdd(
        @Valid MemberDestinationRequestDto memberDestinationRequestDto) {

        memberService.addMemberDestination(memberDestinationRequestDto);

        return "redirect:/mypage/members/me/destinations";
    }

    @GetMapping("/me/memberDestination/{memberNo}/{recipientDestinationNo}/modify")
    public String memberDestinationModifyPageOpen(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("recipientDestinationNo") Long recipientDestinationNo,
        Model model) {

        MemberDestinationResponseDto memberDestinationResponseDto = memberService.findMemberDestinationDetails(recipientDestinationNo);
        memberDestinationResponseDto.setRecipientPhoneNumber(memberDestinationResponseDto.getRecipientPhoneNumber().replace("-", ""));

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("memberDestinationResponseDto", memberDestinationResponseDto);

        return "mypage/member/member-destination-modify";
    }

    @PostMapping("/me/destinations/{recipientDestinationNo}/modify")
    public String memberDestinationModify(
        @PathVariable("recipientDestinationNo") String recipientDestinationNo,
        @Valid MemberDestinationRequestDto memberDestinationRequestDto) {

        memberService.modifyMemberDestination(Long.parseLong(recipientDestinationNo), memberDestinationRequestDto);

        return "redirect:/mypage/members/me/destinations";
    }

    @GetMapping("/me/point-gift")
    public String memberPointGiftSend(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @ModelAttribute MemberPointSendRequestDto memberPointSendRequestDto,
        Model model) {

        Long recentlyPoint = memberService.findMemberRecentlyPoint(userDetailsDto.getMemberNo()).getRemainedPoint();

        model.addAttribute("myMemberNo", userDetailsDto.getMemberNo());
        model.addAttribute("myMemberId", userDetailsDto.getMemberId());
        model.addAttribute("recentlyPoint", recentlyPoint);

        return "/mypage/member/member-point-send";
    }

    @PostMapping("/me/point-gift")
    public String memberPointGiftToMember(
        @Valid MemberPointSendRequestDto memberPointSendRequestDto
        ) {

        Long receiveMemberNo = memberService.findMemberInfo(memberPointSendRequestDto.getReceiveMemberId()).getMemberNo();
        memberPointSendRequestDto.setReceiveMemberNo(receiveMemberNo);

        memberService.giftPointMember(memberPointSendRequestDto);

        return "redirect:/mypage";
    }
}
