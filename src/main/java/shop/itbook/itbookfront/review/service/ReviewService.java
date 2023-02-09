package shop.itbook.itbookfront.review.service;

import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ReviewService {

    PageResponse<ReviewResponseDto> findReviewListByMemberNo(String url,
                                                             Long memberNo);

    Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images);
}
