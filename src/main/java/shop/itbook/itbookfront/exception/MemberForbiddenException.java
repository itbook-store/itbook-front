package shop.itbook.itbookfront.exception;

public class MemberForbiddenException extends RuntimeException {

        public static final String MESSAGE = "죄송합니다 고객님 ";

        public MemberForbiddenException(String resultMessage) {
            super(MESSAGE);
        }
    }