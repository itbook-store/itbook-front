package shop.itbook.itbookfront.bookmark.controller;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    private static final String BASE_DIRECTORY = "mypage/bookmark";

    @GetMapping
    public String bookmarkList(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                               HttpServletResponse response,
                               Model model) throws IOException {

        if (Objects.isNull(userDetailsDto)) {
            response.sendRedirect("/");
        }

        PageResponse<ProductDetailsResponseDto> pageResponse =
            bookmarkService.getBookmark(userDetailsDto.getMemberNo());

        model.addAttribute("pageResponse", pageResponse);


        return BASE_DIRECTORY.concat("/bookmark");
    }

}
