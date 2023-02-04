package shop.itbook.itbookfront.coupon.service.serviceapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl.CouponIssueServiceAdaptor;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueServiceService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponIssueServiceServiceImpl implements CouponIssueServiceService {

    private final CouponIssueServiceAdaptor couponIssueServiceAdaptor;

    @Override
    public PageResponse<UserCouponIssueListResponseDto> findUserAllCouponIssueList(String userAllCouponIssueUrl){

        return couponIssueServiceAdaptor.getUserAllCouponIssueList(userAllCouponIssueUrl);
    }
}
