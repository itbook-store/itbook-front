package shop.itbook.itbookfront.product;

public enum ProductSuccessMessage {
    ADD_PRODUCT_MESSAGE("상품 등록에 성공하였습니다."),
    ADD_BOOK_MESSAGE("도서 등록에 성공하였습니다."),
    MODIFY_PRODUCT_MESSAGE("상품 수정에 성공하였습니다."),
    MODIFY_BOOK_MESSAGE("도서 수정에 성공하였습니다.");

    private String message;

    ProductSuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}