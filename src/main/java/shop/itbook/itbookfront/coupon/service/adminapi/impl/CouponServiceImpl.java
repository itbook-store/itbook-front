package shop.itbook.itbookfront.coupon.service.adminapi.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.adaptor.adminapi.impl.CouponAdminAdaptor;
import shop.itbook.itbookfront.coupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookfront.coupon.exception.CategoryNumberNotFoundException;
import shop.itbook.itbookfront.coupon.exception.MembershipGradeNotFoundException;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponService;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.product.exception.ProductNotFoundException;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponAdminAdaptor couponAdminAdaptor;

    @Override
    public Long addCoupon(CouponInputRequestDto couponInputRequestDto) {

        Long couponNo = couponAdminAdaptor.addCoupon(couponInputRequestDto);

        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
        return couponNo;
    }

    @Override
    public Long addOrderTotalCoupon(CouponInputRequestDto couponInputRequestDto) {

        OrderTotalCouponRequestDto orderTotalCouponRequestDto =
            new OrderTotalCouponRequestDto(couponInputRequestDto);
        Long couponNo = couponAdminAdaptor.addOrderTotalCoupon(orderTotalCouponRequestDto);

        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
        return couponNo;

    }

    @Override
    public Long addCategoryCoupon(CouponInputRequestDto couponInputRequestDto) {

        if (Objects.isNull(couponInputRequestDto.getCategoryNo())){
            throw new CategoryNumberNotFoundException();
        }
        CategoryCouponRequestDto categoryCouponRequestDto =
            new CategoryCouponRequestDto(couponInputRequestDto.getCategoryNo(), couponInputRequestDto);
        Long couponNo = couponAdminAdaptor.addCategoryCoupon(categoryCouponRequestDto);

        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
        return couponNo;

    }

    @Override
    public Long addProductCoupon(CouponInputRequestDto couponInputRequestDto) {

        if (Objects.isNull(couponInputRequestDto.getProductNo())){
            throw new ProductNotFoundException();
        }

        ProductCouponRequestDto productCouponRequestDto =
            new ProductCouponRequestDto(couponInputRequestDto.getProductNo(), couponInputRequestDto);
        Long couponNo = couponAdminAdaptor.addProductCoupon(productCouponRequestDto);

        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
        return couponNo;

    }

    @Override
    public PageResponse<AdminCouponListResponseDto> findCouponList(String couponListUrl) {

        return couponAdminAdaptor.findCouponList(couponListUrl);
    }

    @Override
    public void addMembershipCoupon(Long couponNo, String membershipGrade) {

        if (Objects.isNull(membershipGrade)){
            throw new MembershipGradeNotFoundException();
        }

        couponAdminAdaptor.addMembershipCoupon(couponNo, membershipGrade);
        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException(
                "해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
    }

}
