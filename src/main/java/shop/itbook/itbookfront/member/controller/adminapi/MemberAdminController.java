package shop.itbook.itbookfront.member.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    @GetMapping()
    public String memberList(Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> pageResponse = memberAdminService.findNormalMembers(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/members");

        return "adminpage/member/admin-member-list";
    }

    @GetMapping("/block")
    public String blockMemberList(Model model, @PageableDefault Pageable pageable) {
        PageResponse<MemberAdminResponseDto> pageResponse = memberAdminService.findBlockMembers(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/members/block");

        return "adminpage/member/admin-member-block-list";
    }

    @GetMapping("/withdraw")
    public String withdrawMemberList(Model model, @PageableDefault Pageable pageable) {
        PageResponse<MemberAdminResponseDto> pageResponse = memberAdminService.findWithdrawMembers(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/members/withdraw");

        return "adminpage/member/admin-member-withdraw-list";
    }

    @GetMapping("/{memberId}/info")
    public String memberDetails(@PathVariable("memberId") String memberId,
                                @ModelAttribute("memberStatusChangeRequestDto")
                                MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                Model model) {
        MemberAdminResponseDto member = memberAdminService.findMember(memberId);
        List<MemberRoleResponseDto> memberRoleList =
            memberAdminService.findMemberRoles(member.getMemberNo());

        model.addAttribute("member", member);
        model.addAttribute("memberRoles", memberRoleList);

        return "adminpage/member/admin-member-details-form";
    }

    @PostMapping("/{memberId}/member-role/add")
    public String memberRoleAdd(@PathVariable("memberId") String memberId,
                                @RequestParam("roleName") String roleName) {
        memberAdminService.addMemberRole(memberId, roleName);

        return "redirect:/admin/members/" + memberId + "/info";
    }

    @PostMapping("/{memberNo}/{roleNo}/member-role/delete")
    public String memberRoleDelete(@PathVariable("memberNo") Long memberNo,
                                   @PathVariable("roleNo") Integer roleNo) {
        memberAdminService.deleteMemberRole(memberNo, roleNo);

        return "redirect:/admin/members";
    }

    @GetMapping("/block/{memberId}/info")
    public String blockMemberDetails(@PathVariable("memberId") String memberId,
                                     @ModelAttribute("memberStatusChangeRequestDto")
                                     MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                     Model model) {
        MemberBlockInfoResponseDto member = memberAdminService.findBlockMember(memberId);
        List<MemberRoleResponseDto> memberRoleList =
            memberAdminService.findMemberRoles(member.getMemberNo());

        model.addAttribute("member", member);
        model.addAttribute("memberRoles", memberRoleList);

        return "adminpage/member/admin-block-member-details-form";
    }

    @PostMapping("/{memberId}/modify/member-status")
    public String memberStatusModify(@PathVariable("memberId") String memberId,
                                     @Valid MemberStatusChangeRequestDto memberStatusChangeRequestDto) {

        memberAdminService.updateMemberStatus(memberStatusChangeRequestDto, memberId);

        return "redirect:/admin/members";
    }

    @GetMapping("/search")
    public String memberSearch(@RequestParam("searchRequirement") String searchRequirement,
                               @RequestParam("searchWord") String searchWord,
                               Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> pageResponse =
            memberAdminService.findMembersBySearch(searchRequirement, searchWord, "정상회원",
                String.format("?page=%d&size=%d", pageable.getPageNumber(),
                    pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            "/admin/members/search?searchRequirement=" + searchRequirement + "&searchWord=" +
                searchWord);

        return "adminpage/member/admin-member-list";
    }

    @GetMapping("withdraw/search")
    public String withdrawMemberSearch(@RequestParam("searchRequirement") String searchRequirement,
                                       @RequestParam("searchWord") String searchWord,
                                       Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> memberList =
            memberAdminService.findMembersBySearch(searchRequirement, searchWord, "탈퇴회원",
                String.format("?page=%d&size=%d", pageable.getPageNumber(),
                    pageable.getPageSize()));

        model.addAttribute("pageResponse", memberList);
        model.addAttribute("paginationUrl",
            "/admin/members/withdraw/search?searchRequirement=" + searchRequirement +
                "&searchWord=" + searchWord);


        return "adminpage/member/admin-member-withdraw-list";
    }

    @GetMapping("block/search")
    public String blockMemberSearch(@RequestParam("searchRequirement") String searchRequirement,
                                    @RequestParam("searchWord") String searchWord,
                                    Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> memberList =
            memberAdminService.findMembersBySearch(searchRequirement, searchWord, "차단회원",
                String.format("?page=%d&size=%d", pageable.getPageNumber(),
                    pageable.getPageSize()));

        model.addAttribute("pageResponse", memberList);
        model.addAttribute("paginationUrl",
            "/admin/members/block/search?searchRequirement=" + searchRequirement + "&searchWord=" +
                searchWord);


        return "adminpage/member/admin-member-block-list";
    }
}
