package shop.itbook.itbookfront.bookmark.controller;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;
import shop.itbook.itbookfront.common.response.PageResponse;

/**
 * 즐겨찾기 뷰를 위한 컨트롤러 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    private static final String BASE_DIRECTORY = "mypage/bookmark";


    /**
     * 즐겨찾기 뷰페이지에 상품들을 보여주기 위한 메서드 입니다.
     *
     * @param userDetailsDto Authentication에 등록된 회원 정보 입니다.
     * @param response 만약 Authenticatiuon이 존재하지 않을 경우, 메인페이지로 리다이렉트
     * @param model 뷰 페이지에 객체를 전송하기 위한 모델
     * @return 뷰 페이지
     * @throws IOException for redirect
     * @author 강명관
     */
    @GetMapping
    public String bookmarkList(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                               HttpServletResponse response,
                               Model model) throws IOException {

        if (Objects.isNull(userDetailsDto)) {
            response.sendRedirect("/");
        }

        PageResponse<BookmarkResponseDto> pageResponse =
            bookmarkService.getBookmark(userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);
        model.addAttribute("paginationUrl", BASE_DIRECTORY);


        return BASE_DIRECTORY.concat("/bookmark");
    }

}
