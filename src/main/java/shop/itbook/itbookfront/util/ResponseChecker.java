package shop.itbook.itbookfront.util;

import org.springframework.http.HttpStatus;
import shop.itbook.itbookfront.auth.exception.JwtExpirationException;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.MemberForbiddenException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;

/**
 * @author 최겸준
 * @since 1.0
 */

public class ResponseChecker {

    private ResponseChecker() {

    }

    public static void checkFail(HttpStatus statusCode, String resultMessage) {

        if (statusCode.equals(HttpStatus.BAD_REQUEST)) {
            throw new BadRequestException(resultMessage);
        }

        if (statusCode.equals(HttpStatus.FORBIDDEN)) {
            throw new MemberForbiddenException(resultMessage);
        }

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            throw new RestApiServerException(resultMessage);
        }

        if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            throw new JwtExpirationException();
        }
    }
}
