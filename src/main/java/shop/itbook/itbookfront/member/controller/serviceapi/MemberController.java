package shop.itbook.itbookfront.member.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
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

    @GetMapping("/{memberId}/info")
    public String mypageInfo(@PathVariable("memberId") String memberId, Model model) {

        MemberInfoResponseDto memberInfoResponseDto = memberService.findMemberInfo(memberId);

        model.addAttribute("memberInfoResponseDto", memberInfoResponseDto);

        return "mypage/member/member-info-modify";
    }

    @PostMapping("/modify")
    public String mypageInfoModify(@RequestParam("memberId") String memberId,
                                   @RequestParam("name") String name,
                                   @RequestParam("nickname") String nickname,
                                   @RequestParam("password") String password,
                                   @RequestParam("phoneNumber") String phoneNumber,
                                   @RequestParam("email") String email) {

        memberService.updateMemberInfo(memberId, name, nickname, password, phoneNumber, email);

        return "redirect:/mypage";

    }

    @GetMapping("/{memberId}/withdraw")
    public String memberStatusModifyToWithdraw(@PathVariable("memberId") String memberId,
                                               @ModelAttribute("memberStatusChangeRequestDto")
                                               MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                               Model model) {
        MemberInfoResponseDto member = memberService.findMemberInfo(memberId);
        model.addAttribute("member", member);

        return "mypage/member/member-withdraw";
    }

    @PostMapping("/{memberId}/withdraw")
    public String memberWithdraw(@PathVariable("memberId") String memberId,
                                 @Valid MemberStatusChangeRequestDto memberStatusChangeRequestDto) {

        memberService.withdrawMember(memberId, memberStatusChangeRequestDto);

        return "redirect:/mypage";
    }
}