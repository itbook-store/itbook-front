package shop.itbook.itbookfront.bookmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.bookmark.adaptor.BookmarkAdaptor;
import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.service.BookmarkService;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkAdaptor bookmarkAdaptor;

    @Override
    public boolean addBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return bookmarkAdaptor.addBookmark(bookmarkRequestDto);
    }

    @Override
    public void deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        bookmarkAdaptor.deleteBookmark(bookmarkRequestDto);
    }

    @Override
    public void deleteAllBookmark(Long memberNo) {
        bookmarkAdaptor.deleteAllBookmark(memberNo);
    }

    @Override
    public PageResponse<ProductDetailsResponseDto> getBookmark(Long memberNo) {
        return bookmarkAdaptor.getBookmarkList(memberNo);
    }
}
