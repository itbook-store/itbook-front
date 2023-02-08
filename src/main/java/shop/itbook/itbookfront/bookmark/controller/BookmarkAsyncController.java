package shop.itbook.itbookfront.bookmark.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;

/**
 * 즐겨찾기 비동기 통신을 위한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/bookmark")
public class BookmarkAsyncController {

    private final BookmarkService bookmarkService;

    private static final String FAIL_MESSAGE = "bookmarkFailMessage";


    /**
     * 즐겨찾기에 상품을 추가하는 메서드 입니다.
     *
     * @param bookmarkRequestDto 회원번호, 상품번호
     * @param redirectAttributes Exception 발생 시, redirect 하며 메세지 전송
     * @return 성공 true, 실패 false
     * @author 강명관
     */
    @PostMapping("/add")
    public boolean bookmarkAddProduct(@Valid @RequestBody BookmarkRequestDto bookmarkRequestDto,
                                      RedirectAttributes redirectAttributes
    ) {

        boolean result = false;

        try {
            result = bookmarkService.addBookmark(bookmarkRequestDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(FAIL_MESSAGE, "즐겨찾기 등록에 실패하였습니다.");
        }

        return result;
    }

    /**
     * 즐겨찾기 상품 삭제하는 메서드 입니다.
     *
     * @param bookmarkRequestDto 회원번호, 상품번호
     * @param redirectAttributes Exception 발생 시, redirect 하며 메세지 전송
     * @author 강명관
     */
    @PostMapping("/delete")
    public void bookmarkDeleteProduct(@Valid @RequestBody BookmarkRequestDto bookmarkRequestDto,
                                      RedirectAttributes redirectAttributes) {

        log.info("bookmarkRequestDto {}", bookmarkRequestDto);

        try {
            bookmarkService.deleteBookmark(bookmarkRequestDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(FAIL_MESSAGE, "상품 삭제에 실패 하였습니다.");
        }

    }

    /**
     * 즐겨찾기 해당 회원에 대한 모든 상품을 삭제하는 메서드 입니다.
     *
     * @param memberNo 회원번호
     * @param redirectAttributes Exception 발생 시, redirect 하며 메세지 전송
     * @author 강명관
     */
    @PostMapping("/delete/{memberNo}")
    public void bookmarkDeleteAllProduct(@PathVariable(value = "memberNo") Long memberNo,
                                          RedirectAttributes redirectAttributes) {

        try {
            bookmarkService.deleteAllBookmark(memberNo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(FAIL_MESSAGE, "상품 삭제에 실패 하였습니다.");
        }

    }

}
