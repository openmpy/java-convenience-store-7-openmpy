package store.domain;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyAmount;
    private final int getAmount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(
            final String name,
            final int buyAmount,
            final int getAmount,
            final LocalDate startDate,
            final LocalDate endDate
    ) {
        this.name = name;
        this.buyAmount = buyAmount;
        this.getAmount = getAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getGetAmount() {
        return getAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
