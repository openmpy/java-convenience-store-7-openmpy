package store.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.view.InputView;

public class Order {

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
        cart.getItems().keySet().forEach(product -> {
            final Integer purchaseQuantity = cart.getItems().get(product);
            final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());
            if (promotion == null) {
                return;
            }
            if (!promotion.isApplyDate(date)) {
                return;
            }
            final int buyQuantity = promotion.getBuyQuantity();
            final int getQuantity = promotion.getGetQuantity();
            final int promotionQuantity = product.getPromotionQuantity();
            if (purchaseQuantity == buyQuantity && promotionQuantity >= purchaseQuantity + getQuantity) {
                final String input = InputView.readGetBonusProduct(product.getName(), getQuantity);
                if (input.equalsIgnoreCase("Y")) {
                    orderItems.put(product, purchaseQuantity + getQuantity);
                    bonusItems.put(product, getQuantity);
                    productsToRemove.add(product);
                    return;
                }
            }
            if (promotionQuantity >= purchaseQuantity) {
                orderItems.put(product, purchaseQuantity);
                bonusItems.put(product, promotion.getBonusProduct(purchaseQuantity));
                productsToRemove.add(product);
                return;
            }
            final int remainingQuantity = purchaseQuantity - promotionQuantity;
            final int remainingPromotionQuantity = promotionQuantity % promotion.getTotalQuantity();
            final String input = InputView.readNotApplyBonusProduct(
                    product.getName(), remainingQuantity + remainingPromotionQuantity);
            if (input.equalsIgnoreCase("Y")) {
                orderItems.put(product, purchaseQuantity);
                bonusItems.put(product, promotion.getBonusProduct(promotionQuantity) * promotion.getGetQuantity());
                productsToRemove.add(product);
            }
        });
        productsToRemove.forEach(cart.getItems()::remove);
    }

    public void addMembershipProduct() {
        for (final Product product : cart.getItems().keySet()) {
            membershipItems.put(product, cart.getItems().get(product));
            orderItems.put(product, cart.getItems().get(product));
        }
    }

    public void checkMembershipDiscount() {
        final String input = InputView.readMembershipDiscount();
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
}
