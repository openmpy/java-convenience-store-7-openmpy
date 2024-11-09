package store.domain;

import store.util.NumberFormatter;

public class Product {

    private static final String NULL = "null";
    private static final String STOCK_EMPTY = "재고 없음";
    private static final String QUANTITY_UNIT = "개";

    private final String name;
    private final int price;
    private int defaultQuantity;
    private int promotionQuantity;
    private final String promotionName;

    public Product(
            final String name,
            final int price,
            final int defaultQuantity,
            final int promotionQuantity,
            final String promotionName
    ) {
        this.name = name;
        this.price = price;
        this.defaultQuantity = defaultQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public void decrease(final int quantity) {
        if (promotionQuantity > quantity) {
            promotionQuantity -= quantity;
            return;
        }

        defaultQuantity -= quantity - promotionQuantity;
        promotionQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDefaultQuantity() {
        return defaultQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getTotalQuantity() {
        return defaultQuantity + promotionQuantity;
    }

    public String getFormattedPrice() {
        return NumberFormatter.apply(price);
    }

    public String getQuantityState(final int quantity) {
        if (quantity == 0) {
            return STOCK_EMPTY;
        }
        return NumberFormatter.apply(quantity) + QUANTITY_UNIT;
    }
}
