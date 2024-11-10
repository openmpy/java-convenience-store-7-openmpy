package store.exception;

import store.exception.constants.ErrorConstants;

public class OverProductQuantityException extends IllegalArgumentException {

    private static final String MESSAGE = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    public OverProductQuantityException() {
        super(ErrorConstants.ERROR_PREFIX.getValue() + MESSAGE);
    }
}
