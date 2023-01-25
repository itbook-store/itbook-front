package shop.itbook.itbookfront.member.controller;

import io.lettuce.core.output.ScanOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.dto.response.MemberInfoResponseDto;
import shop.itbook.itbookfront.member.service.MemberInfoService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/mypage/members")
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;


    @GetMapping("/{memberId}/info")
    public String myMemberInfo(@PathVariable("memberId") String memberId, ModelMap model) {

        MemberInfoResponseDto memberInfoResponseDto = memberInfoService.findMemberInfo(memberId);

        model.addAttribute("memberInfoResponseDto", memberInfoResponseDto);

        return "mypage/member/member-info-modify";
    }

    @PostMapping("/modify")
    public String mypageInfoModify( MemberInfoResponseDto memberInfoResponseDto) {

        System.out.println(">>>> " + memberInfoResponseDto.getMemberId());

        MemberUpdateRequestDto memberUpdateRequestDto = memberInfoService.fillInUpdateDto(memberInfoResponseDto);
        memberInfoService.updateMemberInfo(memberUpdateRequestDto, memberInfoResponseDto.getMemberId());

        return "mypage";

    }
}
