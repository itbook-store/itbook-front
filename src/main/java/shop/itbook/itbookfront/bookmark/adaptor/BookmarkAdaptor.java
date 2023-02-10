package shop.itbook.itbookfront.bookmark.adaptor;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookfront.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;

/**
 * 즐겨찾기에 대한 Shop서버와 통신하는 로직을 담고있는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BookmarkAdaptor {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    private static final String GET_BOOKMARK_LIST_API = "/api/bookmark/";

    private static final String ADD_BOOKMARK_API = "/api/bookmark";

    private static final String DELETE_BOOKMARK_API = "/api/bookmark";

    private static final String DELETE_ALL_BOOKMARK_API = "/api/bookmark/";


    public boolean addBookmark(BookmarkRequestDto bookmarkRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> exchange = restTemplate.exchange(
                gatewayConfig.getGatewayServer() + ADD_BOOKMARK_API,
                HttpMethod.POST,
                new HttpEntity<>(bookmarkRequestDto, headers),
                new ParameterizedTypeReference<>() {}
            );

        SuccessfulResponseDto result = Objects.requireNonNull(exchange.getBody()).getResult();

        return result.getIsSuccessful();
    }

    public void deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + DELETE_BOOKMARK_API,
            HttpMethod.DELETE,
            new HttpEntity<>(bookmarkRequestDto, headers),
            new ParameterizedTypeReference<>() {}
        );
    }

    public void deleteAllBookmark(Long memberNo) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + DELETE_ALL_BOOKMARK_API + memberNo,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {
            }
        );

    }

    public PageResponse<BookmarkResponseDto> getBookmarkList(Long memberNo) {

        ResponseEntity<CommonResponseBody<PageResponse<BookmarkResponseDto>>> exchange =
            restTemplate.exchange(
            gatewayConfig.getGatewayServer() + GET_BOOKMARK_LIST_API + memberNo,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            }
        );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

}
