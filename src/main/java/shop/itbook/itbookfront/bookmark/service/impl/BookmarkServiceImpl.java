package shop.itbook.itbookfront.bookmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.bookmark.adaptor.BookmarkAdaptor;
import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;
import shop.itbook.itbookfront.common.response.PageResponse;

/**
 * 즐겨찾기 비지니스 로직을 담고있는 서비스 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkAdaptor bookmarkAdaptor;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return bookmarkAdaptor.addBookmark(bookmarkRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        bookmarkAdaptor.deleteBookmark(bookmarkRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllBookmark(Long memberNo) {
        bookmarkAdaptor.deleteAllBookmark(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<BookmarkResponseDto> getBookmark(Long memberNo) {
        return bookmarkAdaptor.getBookmarkList(memberNo);
    }
}
