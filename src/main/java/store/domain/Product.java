package store.domain;

import store.util.NumberFormatter;

public class Product {

    private static final String NULL = "null";
    private static final String EMPTY = "";
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
            final int quantity,
            final String promotionName
    ) {
        handleQuantity(quantity, promotionName);

        this.name = name;
        this.price = price;
        this.promotionName = handlePromotionName(promotionName);
    }

    private void handleQuantity(final int quantity, final String promotionName) {
        if (promotionName.equals(NULL)) {
            defaultQuantity = quantity;
            return;
        }
        promotionQuantity = quantity;
    }

    private String handlePromotionName(final String promotionName) {
        if (promotionName.equals(NULL)) {
            return null;
        }
        return promotionName;
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

    public int getCustomQuantity() {
        if (promotionName == null) {
            return defaultQuantity;
        }
        return promotionQuantity;
    }

    public String getCustomPromotionName() {
        if (promotionName == null) {
            return EMPTY;
        }
        return promotionName;
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
