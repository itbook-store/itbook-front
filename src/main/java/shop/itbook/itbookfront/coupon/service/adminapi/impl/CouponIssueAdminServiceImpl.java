package shop.itbook.itbookfront.coupon.service.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.adaptor.adminapi.impl.CouponIssueAdminAdaptor;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponIssueAdminService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponIssueAdminServiceImpl implements CouponIssueAdminService {

    private final CouponIssueAdminAdaptor couponIssueAdminAdaptor;

    @Override
    public PageResponse<AdminCouponIssueListResponseDto> findAllCouponIssueList(
        String couponPageableUrl){
        return couponIssueAdminAdaptor.findCouponIssueList(couponPageableUrl);
    }
}
