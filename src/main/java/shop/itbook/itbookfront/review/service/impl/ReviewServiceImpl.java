package shop.itbook.itbookfront.review.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.review.adaptor.ReviewAdaptor;
import shop.itbook.itbookfront.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookfront.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookfront.review.service.ReviewService;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewAdaptor reviewAdaptor;

    @Override
    public PageResponse<ReviewResponseDto> findReviewListByMemberNo(String url, Long memberNo) {

        return reviewAdaptor.getReviewListByMemberNo(url, memberNo);
    }

    @Override
    public Long addReview(ReviewRequestDto reviewRequestDto,
                          MultipartFile images) {

        return reviewAdaptor.addReview(reviewRequestDto, images);
    }

    @Override
    public ReviewResponseDto findReview(Long orderProductNo) {

        return reviewAdaptor.getReview(orderProductNo);
    }

    @Override
    public void deleteReview(Long orderProductNo) {

        reviewAdaptor.deleteReview(orderProductNo);
    }

    @Override
    public void modifyReview(Long orderProductNo, ReviewRequestDto reviewRequestDto,
                             MultipartFile images) {

        reviewAdaptor.modifyReview(orderProductNo, reviewRequestDto, images);
    }
}
