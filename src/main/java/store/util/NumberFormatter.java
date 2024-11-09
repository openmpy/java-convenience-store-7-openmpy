package store.util;

import java.text.NumberFormat;

public class NumberFormatter {

    public static String apply(final int number) {
        return NumberFormat.getNumberInstance().format(number);
    }
}
