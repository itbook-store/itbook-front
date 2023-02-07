package shop.itbook.itbookfront.bookmark.service;

import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */
public interface BookmarkService {

    boolean addBookmark(BookmarkRequestDto bookmarkRequestDto);

    void deleteBookmark(BookmarkRequestDto bookmarkRequestDto);

    void deleteAllBookmark(Long memberNo);

    PageResponse<ProductDetailsResponseDto> getBookmark(Long memberNo);


}
