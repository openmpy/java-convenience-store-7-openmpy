package store.exception;

import store.exception.constants.ErrorConstants;

public class InvalidInputException extends IllegalArgumentException {

    private static final String MESSAGE = "잘못된 입력입니다. 다시 입력해 주세요.";

    public InvalidInputException() {
        super(ErrorConstants.ERROR_PREFIX.getValue() + MESSAGE);
    }
}
