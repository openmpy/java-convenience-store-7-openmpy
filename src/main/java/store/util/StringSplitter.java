package store.util;

import java.util.Arrays;
import java.util.List;

public class StringSplitter {

    private static final String COMMA_DELIMITER = ",";
    private static final int DEFAULT_LIMIT = -1;

    public static List<String> splitComma(final String string) {
        return Arrays.stream(string.split(COMMA_DELIMITER, DEFAULT_LIMIT))
                .map(String::strip)
                .toList();
    }
}
