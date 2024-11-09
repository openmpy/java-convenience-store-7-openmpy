package store.domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import store.view.InputView;

public class Order {

    private final Map<Product, Integer> orderItems = new LinkedHashMap<>();
    private final Map<Product, Integer> bonusItems = new LinkedHashMap<>();
    private final Map<Product, Integer> membershipItems = new LinkedHashMap<>();

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
        for (final Product product : cart.getItems().keySet()) {
            final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());
            if (promotion == null) {
                continue;
            }
            if (cart.getItems().get(product) % promotion.getTotalQuantity() == 0) {
                if (promotion.isApplyDate(date)) {
                    bonusItems.put(product, cart.getItems().get(product) / promotion.getTotalQuantity());
                }
                orderItems.put(product, cart.getItems().get(product));
            }
        }
    }

    public void checkGetBonusProduct(final LocalDate date) {
        for (final Product product : cart.getItems().keySet()) {
            final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());
            if (promotion == null) {
                continue;
            }

            if (cart.getItems().get(product) == promotion.getBuyQuantity()) {
                int bonusQuantity = 0;
                if (promotion.isApplyDate(date)) {
                    final String input = InputView.readGetBonusProduct(product.getName(), promotion.getGetQuantity());
                    if (input.equalsIgnoreCase("Y")) {
                        bonusQuantity = promotion.getGetQuantity();
                        bonusItems.put(product, bonusQuantity);
                    }
                }
                orderItems.put(product, cart.getItems().get(product) + bonusQuantity);
            }
        }
    }

    public void checkNotApplyBonusProduct(final LocalDate date) {
        for (final Product product : cart.getItems().keySet()) {
            final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());
            if (promotion == null) {
                continue;
            }

            if (cart.getItems().get(product) > product.getPromotionQuantity()) {
                final int notApplyPromotionQuantity = cart.getItems().get(product) -
                        promotion.getTotalQuantity() * (product.getPromotionQuantity() / promotion.getTotalQuantity());
                if (promotion.isApplyDate(date)) {
                    final String input = InputView.readNotApplyBonusProduct(product.getName(),
                            notApplyPromotionQuantity);
                    if (input.equalsIgnoreCase("Y")) {
                        final int bonusProductQuantity =
                                (cart.getItems().get(product) - notApplyPromotionQuantity)
                                        / promotion.getTotalQuantity();
                        bonusItems.put(product, bonusProductQuantity);
                    }
                }
                orderItems.put(product, cart.getItems().get(product));
            }
        }
    }

    public void addMembershipProduct() {
        for (final Product product : cart.getItems().keySet()) {
            final Promotion promotion = promotions.findPromotionsByName(product.getPromotionName());
            if (promotion != null) {
                continue;
            }
            orderItems.put(product, cart.getItems().get(product));
            membershipItems.put(product, cart.getItems().get(product));
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
