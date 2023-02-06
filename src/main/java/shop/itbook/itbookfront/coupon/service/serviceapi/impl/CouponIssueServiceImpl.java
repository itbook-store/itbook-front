package shop.itbook.itbookfront.coupon.service.serviceapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl.CouponIssueAdaptor;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponIssueServiceImpl implements CouponIssueService {

    private final CouponIssueAdaptor couponIssueAdaptor;

    @Override
    public PageResponse<UserCouponIssueListResponseDto> findUserAllCouponIssueList(String userAllCouponIssueUrl){

        return couponIssueAdaptor.getUserAllCouponIssueList(userAllCouponIssueUrl);
    }

    @Override
    public void usePointCouponByUser(String usePointCouponIssueUrl){
        couponIssueAdaptor.usePointCoupon(usePointCouponIssueUrl);
    }

    @Override
    public Long addCouponIssueByCouponType(String couponIssueByCouponTypeUrl){

        return couponIssueAdaptor.addCouponByCouponType(couponIssueByCouponTypeUrl);
    }

    @Override
    public List<CouponListResponseDto> getCouponsByCouponType(String couponsByCouponTypeUrl){
        return couponIssueAdaptor.getCouponsByCouponType(couponsByCouponTypeUrl);
    }
}
