package shop.itbook.itbookfront.review.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookfront.review.dto.response.UnwrittenReviewOrderProductResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
public interface ReviewService {

    PageResponse<ReviewResponseDto> findReviewListByMemberNo(String url,
                                                             Long memberNo);

    Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images);

    ReviewResponseDto findReview(Long orderProductNo);

    void deleteReview(Long orderProductNo);

    void modifyReview(Long orderProductNo,
                      ReviewRequestDto reviewRequestDto,
                      MultipartFile images);

    PageResponse<ReviewResponseDto> findReviewListByProductNo(String url,
                                                             Long productNo);

    PageResponse<UnwrittenReviewOrderProductResponseDto> findUnwrittenReviewOrderProductList(String url,
                                                                                             Long memberNo);
}
