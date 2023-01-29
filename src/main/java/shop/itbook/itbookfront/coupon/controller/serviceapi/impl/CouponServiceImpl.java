package shop.itbook.itbookfront.coupon.controller.serviceapi.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.coupon.adaptor.impl.CouponAdaptor;
import shop.itbook.itbookfront.coupon.controller.serviceapi.CouponService;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponAdaptor couponAdaptor;

    @Override
    public void addCoupon(CouponInputRequestDto couponInputRequestDto){
        Long couponNo = couponAdaptor.addCoupon(couponInputRequestDto);

        if (Objects.isNull(couponNo)) {
            throw new RestApiServerException("해당 서버에서 정상적인 등록 번호를 보내지 않았습니다. 등록이 이루어졌는지 확인이 필요합니다.");
        }
    }

    @Override
    public List<CouponListResponseDto> findCouponList(String couponListUrl){

        return couponAdaptor.findCouponList(couponListUrl);
    }
}
