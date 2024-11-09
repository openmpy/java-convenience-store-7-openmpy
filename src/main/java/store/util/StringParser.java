package store.util;

import java.time.LocalDate;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;

public class StringParser {

    private static final String NULL = "null";

    public static Promotion parsePromotion(final String string) {
        final List<String> items = StringSplitter.splitComma(string);
        final String promotionName = items.get(0);
        final int buyAmount = Integer.parseInt(items.get(1));
        final int getAmount = Integer.parseInt(items.get(2));
        final LocalDate startDate = LocalDate.parse(items.get(3));
        final LocalDate endDate = LocalDate.parse(items.get(4));
        return new Promotion(promotionName, buyAmount, getAmount, startDate, endDate);
    }

    public static Product parseProduct(final String string) {
        final List<String> items = StringSplitter.splitComma(string);
        final String productName = items.get(0);
        final int price = Integer.parseInt(items.get(1));
        final int quantity = Integer.parseInt(items.get(2));
        final String promotionName = items.get(3);

        if (promotionName.equals(NULL)) {
            return new Product(productName, price, quantity, 0, null);
        }
        return new Product(productName, price, 0, quantity, promotionName);
    }
}
