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
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
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

        MembershipHistoryResponseDto membershipHistoryResponseDto = membershipHistoryList.get(membershipHistoryList.size()-1);
        Long amountBasedOnNextLevel = 0L;

        if(membershipHistoryResponseDto.getMembershipGrade().equals("일반")) {
            amountBasedOnNextLevel = 100_000L;
        }

        if(membershipHistoryResponseDto.getMembershipGrade().equals("화이트")) {
            amountBasedOnNextLevel = 200_000L;
        }

        if(membershipHistoryResponseDto.getMembershipGrade().equals("실버")) {
            amountBasedOnNextLevel = 300_000L;
        }

        if(membershipHistoryResponseDto.getMembershipGrade().equals("골드")) {
            amountBasedOnNextLevel = 500_000L;
        }

        model.addAttribute("myMembership", membershipHistoryResponseDto.getMembershipGrade());
        model.addAttribute("amountBasedOnNextLevel", amountBasedOnNextLevel);
        model.addAttribute("membershipHistoryList", membershipHistoryList);

        return "mypage/member/member-membership-info";
    }

    @GetMapping()
    public String membershipList(Model model) {

        List<MembershipResponseDto> membershipResponseDtoList = membershipService.getMemberships();

        model.addAttribute("membershipResponseDtoList", membershipResponseDtoList);

        return "mypage/member/membership-list";
    }
}
