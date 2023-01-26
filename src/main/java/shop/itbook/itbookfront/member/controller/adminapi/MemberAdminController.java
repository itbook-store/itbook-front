package shop.itbook.itbookfront.member.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/adminpage/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    @GetMapping()
    public String MemberList(Model model) {
        List<MemberInfoResponseDto> memberInfoResponseDtoList = memberAdminService.findMembers();

        model.addAttribute("memberList", memberInfoResponseDtoList);

        return "adminpage/member/admin-member-list";
    }
}
