package store.domain;

import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    public Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion findPromotionsByName(final String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
