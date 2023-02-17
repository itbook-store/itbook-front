package shop.itbook.itbookfront.coupon.service.serviceapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl.MembershipCouponAdaptor;
import shop.itbook.itbookfront.coupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MembershipCouponServiceImpl implements MembershipCouponService{

    private final MembershipCouponAdaptor membershipCouponAdaptor;

    @Override
    public List<List<MembershipCouponResponseDto>> findAvailableMembershipCoupon(String membershipCouponListUrl){

        return membershipCouponAdaptor.getUserAllCouponIssueList(membershipCouponListUrl);
    }
}
