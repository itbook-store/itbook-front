package shop.itbook.itbookfront.bookmark.service;

import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;

/**
 * 즐겨찾기 서비스로직에 대한 인터페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface BookmarkService {

    /**
     * 즐겨찾기 상품을 등록하는 메서드
     *
     * @param bookmarkRequestDto 회원 번호, 상품 번호
     * @return 성공 true, 실패 false
     * @author 강명관
     */
    boolean addBookmark(BookmarkRequestDto bookmarkRequestDto);


    /**
     * 즐겨찾기 상품을 삭제하는 메서드
     *
     * @param bookmarkRequestDto 회원 번호, 상품 번호
     * @author 강명관
     */
    void deleteBookmark(BookmarkRequestDto bookmarkRequestDto);

    /**
     * 즐겨찾기 해당 회원번호의 모든 상품을 삭제하느 메서드.
     *
     * @param memberNo 회원 번호
     * @author 강명관
     */
    void deleteAllBookmark(Long memberNo);

    /**
     * 즐겨찾기 해당 회원의 모든 상품을 페이지네이션 처리하여 갖고오는 메서드
     *
     * @param memberNo 회원 번호
     * @return 페이지네이션 처리된 상품 상세 정보 리스트
     * @author 강명관
     */
    PageResponse<BookmarkResponseDto> getBookmark(Long memberNo);


}
