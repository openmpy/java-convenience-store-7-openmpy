package store.domain;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyQuantity;
    private final int getQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(
            final String name,
            final int buyQuantity,
            final int getQuantity,
            final LocalDate startDate,
            final LocalDate endDate
    ) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplyDate(final LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalQuantity() {
        return buyQuantity + getQuantity;
    }

    public int getBonusProduct(int quantity) {
        if (quantity == buyQuantity) {
            return getQuantity;
        }
        return quantity / getTotalQuantity();
    }
}
