package store.exception;

import store.exception.constants.ErrorConstants;

public class NotFoundProductException extends IllegalArgumentException {

    private static final String MESSAGE = "존재하지 않는 상품입니다. 다시 입력해 주세요.";

    public NotFoundProductException() {
        super(ErrorConstants.ERROR_PREFIX.getValue() + MESSAGE);
    }
}
