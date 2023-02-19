package shop.itbook.itbookfront.member.controller.adminapi;

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
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.request.MemberRegisterWriterRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberSearchRequestDto;
import shop.itbook.itbookfront.member.dto.request.MemberStatusChangeRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberAdminResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberBlockInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;

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

    private final MemberService memberService;

    @GetMapping()
    public String memberList(Model model, @PageableDefault Pageable pageable,
                             @ModelAttribute("memberSearchRequestDto")
                             MemberSearchRequestDto memberSearchRequestDto) {

        PageResponse<MemberAdminResponseDto> pageResponse = memberAdminService.findNormalMembers(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));


        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/members");

        return "adminpage/member/admin-member-list";
    }

    @GetMapping("/writer")
    public String writerMemberList(Model model, @PageableDefault Pageable pageable) {
        PageResponse<MemberAdminResponseDto> pageResponse = memberAdminService.findWriterMembers(
            String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/admin/members/writer");

        return "adminpage/member/admin-member-writer-list";
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

    @GetMapping("/{memberNo}/info")
    public String memberDetails(@PathVariable("memberNo") Long memberNo,
                                @ModelAttribute("memberStatusChangeRequestDto")
                                MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                Model model) {
        MemberAdminResponseDto member = memberAdminService.findMember(memberNo);
        List<MemberRoleResponseDto> memberRoleList =
            memberAdminService.findMemberRoles(member.getMemberNo());

        model.addAttribute("member", member);
        model.addAttribute("memberRoles", memberRoleList);

        return "adminpage/member/admin-member-details-form";
    }

    @PostMapping("/{memberNo}/member-role/add")
    public String memberRoleAdd(@PathVariable("memberNo") Long memberNo,
                                @RequestParam("roleName") String roleName) {
        memberAdminService.addMemberRole(memberNo, roleName);

        return "redirect:/admin/members/" + memberNo + "/info";
    }

    @PostMapping("/{memberNo}/{roleNo}/member-role/delete")
    public String memberRoleDelete(@PathVariable("memberNo") Long memberNo,
                                   @PathVariable("roleNo") Integer roleNo) {
        memberAdminService.deleteMemberRole(memberNo, roleNo);

        return "redirect:/admin/members";
    }

    @GetMapping("/block/{memberNo}/info")
    public String blockMemberDetails(@PathVariable("memberNo") Long memberNo,
                                     @ModelAttribute("memberStatusChangeRequestDto")
                                     MemberStatusChangeRequestDto memberStatusChangeRequestDto,
                                     Model model) {
        MemberBlockInfoResponseDto member = memberAdminService.findBlockMember(memberNo);
        List<MemberRoleResponseDto> memberRoleList =
            memberAdminService.findMemberRoles(member.getMemberNo());

        model.addAttribute("member", member);
        model.addAttribute("memberRoles", memberRoleList);

        return "adminpage/member/admin-block-member-details-form";
    }

    @PostMapping("/{memberNo}/modify/member-status")
    public String memberStatusModify(@PathVariable("memberNo") Long memberNo,
                                     @Valid MemberStatusChangeRequestDto memberStatusChangeRequestDto) {

        memberAdminService.updateMemberStatus(memberStatusChangeRequestDto, memberNo);

        return "redirect:/admin/members/block";
    }

    @GetMapping("/search")
    public String memberSearch(@Valid MemberSearchRequestDto memberSearchRequestDto,
                               Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> pageResponse;

        if (memberSearchRequestDto.getSearchRequirement().equals("dateOfJoining")) {
            pageResponse =
                memberAdminService.findMemberByDateOfJoining(memberSearchRequestDto, "정상회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));

            model.addAttribute("paginationUrl",
                "/admin/members/search?start=" +
                    memberSearchRequestDto.getStart() + "&end=" +
                    memberSearchRequestDto.getEnd() + "&searchRequirement=" +
                    memberSearchRequestDto.getSearchRequirement() + "&searchWord=");
        } else {
            pageResponse =
                memberAdminService.findMembersBySearch(
                    memberSearchRequestDto.getSearchRequirement(),
                    memberSearchRequestDto.getSearchWord(), "정상회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));

            model.addAttribute("paginationUrl",
                "/admin/members/search?searchRequirement=" +
                    memberSearchRequestDto.getSearchRequirement() + "&searchWord=" +
                    memberSearchRequestDto.getSearchWord());
        }

        model.addAttribute("pageResponse", pageResponse);

        return "adminpage/member/admin-member-list";
    }

    @GetMapping("/withdraw/search")
    public String withdrawMemberSearch(@Valid MemberSearchRequestDto memberSearchRequestDto,
                                       Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> pageResponse;

        if (memberSearchRequestDto.getSearchRequirement().equals("dateOfJoining")) {
            pageResponse =
                memberAdminService.findMemberByDateOfJoining(memberSearchRequestDto, "탈퇴회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        } else {
            pageResponse =
                memberAdminService.findMembersBySearch(
                    memberSearchRequestDto.getSearchRequirement(),
                    memberSearchRequestDto.getSearchWord(), "탈퇴회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        }

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            "/admin/members/withdraw/search?searchRequirement=" +
                memberSearchRequestDto.getSearchRequirement() +
                "&searchWord=" + memberSearchRequestDto.getSearchWord());


        return "adminpage/member/admin-member-withdraw-list";
    }

    @GetMapping("/block/search")
    public String blockMemberSearch(@Valid MemberSearchRequestDto memberSearchRequestDto,
                                    Model model, @PageableDefault Pageable pageable) {

        PageResponse<MemberAdminResponseDto> pageResponse;

        if (memberSearchRequestDto.getSearchRequirement().equals("dateOfJoining")) {
            pageResponse =
                memberAdminService.findMemberByDateOfJoining(memberSearchRequestDto, "차단회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        } else {
            pageResponse =
                memberAdminService.findMembersBySearch(
                    memberSearchRequestDto.getSearchRequirement(),
                    memberSearchRequestDto.getSearchWord(), "차단회원",
                    String.format("?page=%d&size=%d", pageable.getPageNumber(),
                        pageable.getPageSize()));
        }

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl",
            "/admin/members/block/search?searchRequirement=" +
                memberSearchRequestDto.getSearchRequirement() + "&searchWord=" +
                memberSearchRequestDto.getSearchWord());

        return "adminpage/member/admin-member-block-list";
    }

    @GetMapping("/count")
    public String memberCount(Model model) {

        MemberCountResponseDto count = memberAdminService.countMemberByMemberStatus();

        model.addAttribute("count", count);
        model.addAttribute("normalCount", count.getMemberCount() -
            (count.getBlockMemberCount() + count.getWithdrawMemberCount()));

        return "adminpage/member/admin-member-statistics";
    }

    @GetMapping("/membership/count")
    public String memberCountByMembership(Model model) {

        MemberCountByMembershipResponseDto count = memberAdminService.countMemberByMembership();

        model.addAttribute("count", count);

        return "adminpage/member/admin-member-membership-statistics";
    }

    @GetMapping("/register/writer")
    public String memberRegisterWriterForm(Model model,
                                           @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        MemberRegisterWriterRequestDto memberRegisterWriterRequestDto =
            new MemberRegisterWriterRequestDto();

        model.addAttribute("memberId", userDetailsDto.getMemberId());
        model.addAttribute("memberRegisterWriterRequestDto", memberRegisterWriterRequestDto);

        return "adminpage/member/admin-member-writer-register";
    }

    @PostMapping("/writer/register")
    public String memberRegisterWriter(
        @Valid @ModelAttribute MemberRegisterWriterRequestDto memberRegisterWriterRequestDto) {

        MemberInfoResponseDto member =
            memberService.findMemberByMemberId(memberRegisterWriterRequestDto.getMemberId());
        memberRegisterWriterRequestDto.setMemberNo(member.getMemberNo());

        memberAdminService.modifyMemberWriter(memberRegisterWriterRequestDto);

        return "redirect:/admin/members/writer";
    }
}
