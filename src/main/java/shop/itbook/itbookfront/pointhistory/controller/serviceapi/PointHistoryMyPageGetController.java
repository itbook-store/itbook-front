package shop.itbook.itbookfront.pointhistory.controller.serviceapi;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;
import shop.itbook.itbookfront.pointhistory.pointincreasedecreasecontentenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookfront.pointhistory.service.serviceapi.PointHistoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/point-histories")
@RequiredArgsConstructor
public class PointHistoryMyPageGetController {

    public static final String DIRECTORY_PATH = "mypage/pointhistory";

    private final PointHistoryService pointHistoryMemberService;

    @GetMapping("/{pointHistoryNo}")
    public String pointDetails(@PathVariable Long pointHistoryNo, @RequestParam String content, HttpSession session, @PageableDefault Pageable pageable) {

        PointIncreaseDecreaseContentEnum pointContentEnum =
            PointIncreaseDecreaseContentEnum.stringToEnum(content);

        session.setAttribute("memberPointHistoryPage", pageable.getPageNumber());
        String format = String.format(pointContentEnum.getMemberPagePointHistoryDetailsRedirectUrl(), pointHistoryNo);
        return Strings.concat("redirect:", format);
    }

    @GetMapping("/show-content/my-point-list")
    public String myPointHistoryList(@PageableDefault Pageable pageable, @RequestParam(required = false) String content, Model model, @AuthenticationPrincipal
                                     UserDetailsDto userDetailsDto) {

        String format =
            String.format("/api/point-histories/my-point/%d?page=%d&size=%d&content=%s", userDetailsDto.getMemberNo(), pageable.getPageNumber(),
                pageable.getPageSize(), content);

        PageResponse<PointHistoryListDto> pageResponse = pointHistoryMemberService.findPointHistoryList(format);
        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", "/point-histories/show-content/my-point-list");
        model.addAttribute("content", content);
        return Strings.concat(DIRECTORY_PATH, "/myPointHistoryList");
    }
}
