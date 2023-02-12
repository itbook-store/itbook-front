package shop.itbook.itbookfront.pointhistory.pointincreasedecreasecontentenum;

import lombok.Getter;

/**
 * 상태정보를 조회하거나 사용할때 타입안정성을 유지하기 위한 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum PointIncreaseDecreaseContentEnum {

    GIFT("선물", "/admin/point-histories/%d/gift-details", "/point-histories/%d/gift-details"),
    ORDER("주문", "/admin/point-histories/%d/order-details", "/point-histories/%d/order-details"),
    ORDER_CANCEL("주문취소", "/admin/point-histories/%d/order-details", "/admin/point-histories/%d/order-details"),
    GRADE("등급", "/admin/point-histories/%d/grade-details", "/point-histories/%d/grade-details"),
    COUPON("쿠폰", "/admin/point-histories/%d/coupon-details", "/point-histories/%d/coupon-details"),
    REVIEW("리뷰", "/admin/point-histories/%d/review-details", "/point-histories/%d/review-details");

    private final String content;
    private final String adminPagePointHistoryDetailsRedirectUrl;
    private final String memberPagePointHistoryDetailsRedirectUrl;


    PointIncreaseDecreaseContentEnum(String content, String adminPagePointHistoryDetailsRedirectUrl, String memberPagePointHistoryDetailsRedirectUrl) {
        this.content = content;
        this.adminPagePointHistoryDetailsRedirectUrl = adminPagePointHistoryDetailsRedirectUrl;
        this.memberPagePointHistoryDetailsRedirectUrl = memberPagePointHistoryDetailsRedirectUrl;
    }

    public static PointIncreaseDecreaseContentEnum stringToEnum(String content) {

        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum = null;

        for (PointIncreaseDecreaseContentEnum value : PointIncreaseDecreaseContentEnum.values()) {
            if (value.getContent().equals(content)) {
                pointIncreaseDecreaseContentEnum = value;
                break;
            }
        }

        return pointIncreaseDecreaseContentEnum;
    }
}
