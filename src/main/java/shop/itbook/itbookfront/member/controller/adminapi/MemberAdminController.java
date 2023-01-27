package shop.itbook.itbookfront.member.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    @GetMapping()
    public String memberList(Model model) {

        List<MemberAdminResponseDto> memberList = memberAdminService.findMembers();

        model.addAttribute("memberList", memberList);

        return "adminpage/member/admin-member-list";
    }

    @GetMapping("/block")
    public String blockMemberList(Model model) {
        List<MemberAdminResponseDto> memberList = memberAdminService.findMembers();

        model.addAttribute("memberList", memberList);

        return "adminpage/member/admin-member-block-list";
    }

    @GetMapping("/{memberId}/info")
    public String memberDetails(@PathVariable("memberId")String memberId,
                                Model model) {
         MemberAdminResponseDto member = memberAdminService.findMember(memberId);

         model.addAttribute("member", member);

         return "adminpage/member/admin-member-details-form";
    }

    @PostMapping("/{memberId}/modify/member-status")
    public String memberStatusModify(@PathVariable("memberId") String memberId, @RequestParam("memberStatusName") String memberStatusName) {

        memberAdminService.updateMemberStatus(memberStatusName, memberId);

        return "redirect:/admin/members";
    }
}
