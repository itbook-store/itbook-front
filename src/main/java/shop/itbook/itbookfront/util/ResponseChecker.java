package shop.itbook.itbookfront.util;

import shop.itbook.itbookfront.category.adaptor.impl.BadRequestException;
import shop.itbook.itbookfront.category.adaptor.impl.RestApiServerException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.exception.MemberForbiddenException;

/**
 * @author 최겸준
 * @since 1.0
 */
public class ResponseChecker {
    public static void checkFail(CommonResponseBody.CommonHeader header) {

        if (!header.getIsSuccessful()) {
            if (header.getResultCode().equals(400)) {
                new BadRequestException(header.getResultMessage());
                throw new BadRequestException(header.getResultMessage());
            }

            if (header.getResultCode().equals(403)) {
                throw new MemberForbiddenException(header.getResultMessage());
            }

            if (header.getResultCode().equals(500)) {
                throw new RestApiServerException(header.getResultMessage());
            }
        }
    }
}
