package shop.itbook.itbookfront.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;

/**
 * @author 강명관
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private static final String BASE_DIRECTORY = "mypage/bookmark";

    @GetMapping
    public String bookmarkList(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        return BASE_DIRECTORY.concat("/bookmark");
    }

}
