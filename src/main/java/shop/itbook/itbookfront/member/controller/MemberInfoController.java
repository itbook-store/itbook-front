package shop.itbook.itbookfront.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookfront.member.service.MemberInfoService;

/**
 * @author 노수연
 * @since 1.0
 */
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberInfoService memberInfoService;

    @GetMapping()
    public String myMemberInfo(@ModelAttribute("memberUpdateRequestDto")MemberUpdateRequestDto memberUpdateRequestDto) {
        return "mypage/member/member-info-modify";
    }
}
