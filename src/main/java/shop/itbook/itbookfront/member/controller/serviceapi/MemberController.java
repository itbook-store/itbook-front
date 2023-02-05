package shop.itbook.itbookfront.member.controller.serviceapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/mypage/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me/info")
    public String mypageInfo(Model model,
                             @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        MemberInfoResponseDto memberInfoResponseDto = memberService.findMemberInfo(userDetailsDto.getMemberId());

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
                                               @AuthenticationPrincipal UserDetailsDto userDetailsDto,
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
                                        Model model) {

        List<MemberDestinationResponseDto> memberDestinationList = memberService.findMemberDestinationList(
            userDetailsDto.getMemberNo());

        model.addAttribute("memberDestinationList", memberDestinationList);

        return "mypage/member/member-destination-list";
    }
}
