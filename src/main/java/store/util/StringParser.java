package store.util;

import java.time.LocalDate;
import java.util.List;
import store.domain.Promotion;

public class StringParser {

    public static Promotion parsePromotion(final String string) {
        final List<String> items = StringSplitter.splitComma(string);
        final String promotionName = items.get(0);
        final int buyAmount = Integer.parseInt(items.get(1));
        final int getAmount = Integer.parseInt(items.get(2));
        final LocalDate startDate = LocalDate.parse(items.get(3));
        final LocalDate endDate = LocalDate.parse(items.get(4));
        return new Promotion(promotionName, buyAmount, getAmount, startDate, endDate);
    }
}
