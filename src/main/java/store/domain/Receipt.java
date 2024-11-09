package store.domain;

import store.util.NumberFormatter;

public class Receipt {

    private final Order order;

    public Receipt(final Order order) {
        this.order = order;
    }

    public void printResult() {
        System.out.println();
        System.out.println("===========W 편의점=============");
        printProductPurchaseInfo();
        printBonusProductInfo();
        printTotalCalculate();
    }

    private void printProductPurchaseInfo() {
        System.out.println("상품명 수량 금액");
        for (final Product product : order.getOrderItems().keySet()) {
            final Integer buyQuantity = order.getOrderItems().get(product);
            final String formatted = String.format("%s %d %s", product.getName(), buyQuantity,
                    NumberFormatter.apply(buyQuantity * product.getPrice()));
            System.out.println(formatted);
        }
    }

    private void printBonusProductInfo() {
        if (order.getBonusItems().isEmpty()) {
            return;
        }

        System.out.println("===========증\t정=============");
        for (final Product product : order.getBonusItems().keySet()) {
            final String formatted = String.format("%s %d", product.getName(), order.getBonusItems().get(product));
            System.out.println(formatted);
        }
    }

    private void printTotalCalculate() {
        System.out.println("==============================");
        final String totalPriceInfo =
                String.format("총구매액 %d %s", getTotalPurchaseQuantity(), NumberFormatter.apply(getTotalPurchasePrice()));
        final String totalBonusPriceInfo =
                String.format("행사할인 -%s", NumberFormatter.apply(getTotalBonusProductPrice()));
        final String totalMembershipDiscountInfo =
                String.format("멤버십할인 -%s", NumberFormatter.apply(getTotalMembershipPrice()));
        final String sendMoneyInfo = String.format("내실돈 %s",
                NumberFormatter.apply(
                        getTotalPurchasePrice() - getTotalBonusProductPrice() - getTotalMembershipPrice()));

        System.out.println(totalPriceInfo);
        System.out.println(totalBonusPriceInfo);
        System.out.println(totalMembershipDiscountInfo);
        System.out.println(sendMoneyInfo);
    }

    private int getTotalPurchaseQuantity() {
        return order.getOrderItems().values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int getTotalPurchasePrice() {
        return order.getOrderItems().entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();
    }

    private int getTotalBonusProductPrice() {
        return order.getBonusItems().entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();
    }

    private int getTotalMembershipPrice() {
        if (!order.isMembershipDiscount()) {
            return 0;
        }

        final int totalMembershipPrice = order.getMembershipItems().entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue() * entry.getKey().getPrice())
                .sum();

        return (int) Math.min(totalMembershipPrice * 0.3, 8000);
    }
}
