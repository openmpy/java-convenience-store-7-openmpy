package store.domain;

import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    public Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
