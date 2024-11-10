package store.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.view.InputView;

public class Order {

    private static final double DISCOUNT_RATE = 0.3;
    private static final int MAX_MEMBERSHIP_PRICE = 8000;

    private final Map<Product, Integer> orderItems = new LinkedHashMap<>();
    private final Map<Product, Integer> bonusItems = new LinkedHashMap<>();
    private final Map<Product, Integer> membershipItems = new LinkedHashMap<>();
    private final List<Product> productsToRemove = new ArrayList<>();

    private final Promotions promotions;
    private final Products products;
    private final Cart cart;

    private boolean isMembershipDiscount = false;

    public Order(final Promotions promotions, final Products products, final Cart cart) {
        this.promotions = promotions;
        this.products = products;
        this.cart = cart;
    }

    public void checkBonusProduct(final LocalDate date) {
        cart.getItems().keySet().forEach(product -> processProduct(product, date));
        removeProcessedProducts();
    }

    public void addMembershipProduct() {
        for (final Product product : cart.getItems().keySet()) {
            membershipItems.put(product, cart.getItems().get(product));
            orderItems.put(product, cart.getItems().get(product));
        }
    }

    public void checkMembershipDiscount() {
        final String input = InputView.readMembershipDiscount();
        if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
        if (input.equalsIgnoreCase("Y")) {
            isMembershipDiscount = true;
        }
    }

    public void validateProductPurchase() {
        orderItems.keySet().forEach(product -> {
            final Product findProduct = products.findProductsByName(product.getName());
            if (orderItems.get(product) > findProduct.getTotalQuantity()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        });
    }

    private void processProduct(final Product product, final LocalDate date) {
        final Integer purchaseQuantity = cart.getItems().get(product);
        final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());

        if (promotion == null || !promotion.isApplyDate(date)) {
            return;
        }
        applyBonusIfEligible(product, purchaseQuantity, promotion);
    }

    private void applyBonusIfEligible(final Product product, final int purchaseQuantity, final Promotion promotion) {
        if (isEligibleForBonus(product, promotion, purchaseQuantity)) {
            applyBonus(product, purchaseQuantity, promotion);
            return;
        }
        applyNonBonus(product, purchaseQuantity, promotion);
    }

    private boolean isEligibleForBonus(final Product product, final Promotion promotion, final int purchaseQuantity) {
        return promotion.getBuyQuantity() == purchaseQuantity &&
                product.getPromotionQuantity() >= purchaseQuantity + promotion.getGetQuantity();
    }

    private void applyBonus(final Product product, final int purchaseQuantity, final Promotion promotion) {
        final String input = InputView.readGetBonusProduct(product.getName(), promotion.getGetQuantity());
        if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
        if (input.equalsIgnoreCase("Y")) {
            orderItems.put(product, purchaseQuantity + promotion.getGetQuantity());
            bonusItems.put(product, promotion.getGetQuantity());
            productsToRemove.add(product);
        }
    }

    private void applyNonBonus(final Product product, final int purchaseQuantity, final Promotion promotion) {
        final int promotionQuantity = product.getPromotionQuantity();
        if (promotionQuantity >= purchaseQuantity) {
            orderItems.put(product, purchaseQuantity);
            bonusItems.put(product, promotion.getBonusProduct(purchaseQuantity));
            productsToRemove.add(product);
            return;
        }
        handleRemainingQuantity(product, purchaseQuantity, promotion, promotionQuantity);
    }

    private void handleRemainingQuantity(
            final Product product, final int purchaseQuantity, final Promotion promotion, final int promotionQuantity
    ) {
        final int remainingQuantity = purchaseQuantity - promotionQuantity;
        final int remainingPromotionQuantity = promotionQuantity % promotion.getTotalQuantity();
        final String input = InputView.readNotApplyBonusProduct(
                product.getName(), remainingQuantity + remainingPromotionQuantity);
        if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
        if (input.equalsIgnoreCase("Y")) {
            orderItems.put(product, purchaseQuantity);
            bonusItems.put(product, promotion.getBonusProduct(promotionQuantity) * promotion.getGetQuantity());
            productsToRemove.add(product);
        }
    }

    private void removeProcessedProducts() {
        productsToRemove.forEach(cart.getItems()::remove);
    }

    public void settle() {
        orderItems.keySet().forEach(product -> product.decrease(orderItems.get(product)));
    }

    public Map<Product, Integer> getOrderItems() {
        return orderItems;
    }

    public Map<Product, Integer> getBonusItems() {
        return bonusItems;
    }

    public Map<Product, Integer> getMembershipItems() {
        return membershipItems;
    }

    public boolean isMembershipDiscount() {
        return isMembershipDiscount;
    }

    public int getTotalPurchaseQuantity() {
        return orderItems.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getTotalPurchasePrice() {
        return orderItems.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();
    }

    public int getTotalBonusProductPrice() {
        return bonusItems.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();
    }

    public int getTotalMembershipPrice() {
        if (!isMembershipDiscount) {
            return 0;
        }
        final int totalMembershipPrice = membershipItems.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();
        return (int) Math.min(totalMembershipPrice * DISCOUNT_RATE, MAX_MEMBERSHIP_PRICE);
    }
}
