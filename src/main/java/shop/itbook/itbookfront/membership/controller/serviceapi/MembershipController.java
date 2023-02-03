package shop.itbook.itbookfront.membership.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.membership.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookfront.membership.service.MembershipService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/mypage/membership")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/history")
    public String membershipHistoryList(Model model, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        List<MembershipHistoryResponseDto> membershipHistoryList = membershipService
            .getMembershipHistories(userDetailsDto.getMemberId());

        MembershipHistoryResponseDto membershipHistoryResponseDto = membershipHistoryList.get(0);

        model.addAttribute("myMembership", membershipHistoryResponseDto.getMembershipGrade());
        model.addAttribute("membershipHistoryList", membershipHistoryList);

        return "mypage/member/membership-info";
    }
}
