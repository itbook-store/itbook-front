package shop.itbook.itbookfront.bookmark.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;

/**
 * @author 강명관
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/bookmark")
public class BookmarkAsyncController {

    private final BookmarkService bookmarkService;

    @PostMapping("/add")
    public boolean bookmarkAddProduct(@Valid @RequestBody BookmarkRequestDto bookmarkRequestDto,
                                      RedirectAttributes redirectAttributes
    ) {

        boolean result = false;

        try {
            result = bookmarkService.addBookmark(bookmarkRequestDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("bookmarkFailMessage", "즐겨찾기 등록에 실패하였습니다.");
        }

        return result;
    }

    @PostMapping("/delete")
    public void bookmarktDeleteProduct(@Valid @RequestBody BookmarkRequestDto bookmarkRequestDto,
                                      RedirectAttributes redirectAttributes) {

        try {
            bookmarkService.deleteBookmark(bookmarkRequestDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("bookmarkFailMessage", "상품 삭제에 실패 하였습니다.");
        }

    }

    @PostMapping("/delete/{memberNo}")
    public void bookmarktDeleteAllProduct(@PathVariable(value = "memberNo") Long memberNo,
                                          RedirectAttributes redirectAttributes) {

        try {
            bookmarkService.deleteAllBookmark(memberNo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("bookmarkFailMessage", "상품 삭제에 실패 하였습니다.");
        }

    }

}
