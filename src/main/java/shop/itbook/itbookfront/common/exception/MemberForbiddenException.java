package shop.itbook.itbookfront.common.exception;

public class MemberForbiddenException extends RuntimeException {

        public static final String MESSAGE = "고객님의 접근 권한이 부족합니다.";

        public MemberForbiddenException(String resultMessage) {
            super(MESSAGE);
        }
    }