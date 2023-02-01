package shop.itbook.itbookfront.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponListDto {
    private CouponListResponseDto coupon;
    private CategoryListResponseDto category;
}