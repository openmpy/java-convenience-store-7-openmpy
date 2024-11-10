package store.exception.constants;

public enum ErrorConstants {

    ERROR_PREFIX("[ERROR] "),
    ;

    private final String value;

    ErrorConstants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
