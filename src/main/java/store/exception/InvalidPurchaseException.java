package store.exception;

import store.exception.constants.ErrorConstants;

public class InvalidPurchaseException extends IllegalArgumentException {

    private static final String MESSAGE = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";

    public InvalidPurchaseException() {
        super(ErrorConstants.ERROR_PREFIX.getValue() + MESSAGE);
    }
}
