package shop.itbook.itbookfront.util;

import org.springframework.http.HttpStatus;
import shop.itbook.itbookfront.category.adaptor.impl.BadRequestException;
import shop.itbook.itbookfront.category.adaptor.impl.RestApiServerException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.exception.MemberForbiddenException;

/**
 * @author 최겸준
 * @since 1.0
 */
public class ResponseChecker {

    public static void checkFail(HttpStatus statusCode, CommonResponseBody.CommonHeader header) {

        if (statusCode.equals(HttpStatus.OK)) {
            throw new BadRequestException(header.getResultMessage());
        }

        if (statusCode.equals(HttpStatus.FORBIDDEN)) {
            throw new MemberForbiddenException(header.getResultMessage());
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            throw new RestApiServerException(header.getResultMessage());
        }
    }
}
