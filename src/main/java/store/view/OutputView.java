package store.view;

import store.domain.Order;
import store.domain.Product;
import store.domain.Products;
import store.util.NumberFormatter;

public class OutputView {

    public static void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public static void printProductsInfo(final Products products) {
        for (final Product product : products.getProducts()) {
            printProductInfoWithPromotion(product);
            printProductInfo(product);
        }
        System.out.println();
    }

    public static void printStoreReceiptHeader() {
        System.out.println();
        System.out.println("===========W 편의점=============");
    }

    public static void printProductPurchaseInfo(final Order order) {
        System.out.printf("%-10s %-5s %-5s %n", "상품명", "수량", "금액");
        for (final Product product : order.getOrderItems().keySet()) {
            final Integer buyQuantity = order.getOrderItems().get(product);
            final String formatted = String.format("%-10s %-5d %-5s",
                    product.getName(), buyQuantity, NumberFormatter.apply(buyQuantity * product.getPrice()));
            System.out.println(formatted);
        }
    }

    public static void printBonusProductInfo(final Order order) {
        if (order.getBonusItems().isEmpty()) {
            return;
        }
        System.out.println("===========증\t정=============");
        for (final Product product : order.getBonusItems().keySet()) {
            final String formatted = String.format("%-10s %-5d", product.getName(), order.getBonusItems().get(product));
            System.out.println(formatted);
        }
    }

    public static void printTotalCalculate(final Order order) {
        System.out.println("==============================");
        printReceiptTotalPrice(order);
        printReceiptBonusPrice(order);
        printReceiptMembershipDiscount(order);
        printReceiptSendMoney(order);
    }

    private static void printProductInfoWithPromotion(final Product product) {
        if (product.getPromotionName() != null) {
            final String formatted = String.format("- %s %s원 %s %s",
                    product.getName(),
                    product.getFormattedPrice(),
                    product.getQuantityState(product.getPromotionQuantity()),
                    product.getPromotionName()
            );
            System.out.println(formatted);
        }
    }

    private static void printProductInfo(final Product product) {
        final String formatted = String.format("- %s %s원 %s",
                product.getName(),
                product.getFormattedPrice(),
                product.getQuantityState(product.getDefaultQuantity())
        );
        System.out.println(formatted);
    }

    private static void printReceiptTotalPrice(final Order order) {
        final int totalPurchaseQuantity = order.getTotalPurchaseQuantity();
        final int totalPurchasePrice = order.getTotalPurchasePrice();
        final String totalPriceInfo =
                String.format("%-10s %-5d %-5s",
                        "총구매액", totalPurchaseQuantity, NumberFormatter.apply(totalPurchasePrice));
        System.out.println(totalPriceInfo);
    }

    private static void printReceiptBonusPrice(final Order order) {
        final int totalBonusProductPrice = order.getTotalBonusProductPrice();
        final String totalBonusPriceInfo =
                String.format("%-10s -%-5s", "행사할인", NumberFormatter.apply(totalBonusProductPrice));
        System.out.println(totalBonusPriceInfo);
    }

    private static void printReceiptMembershipDiscount(final Order order) {
        final int totalMembershipPrice = order.getTotalMembershipPrice();
        final String totalMembershipDiscountInfo =
                String.format("%-10s -%-5s", "멤버십할인", NumberFormatter.apply(totalMembershipPrice));
        System.out.println(totalMembershipDiscountInfo);
    }

    private static void printReceiptSendMoney(final Order order) {
        final int totalPurchasePrice = order.getTotalPurchasePrice();
        final int totalBonusProductPrice = order.getTotalBonusProductPrice();
        final int totalMembershipPrice = order.getTotalMembershipPrice();

        final String sendMoneyInfo = String.format(
                "%-10s %-5s",
                "내실돈",
                NumberFormatter.apply(totalPurchasePrice - totalBonusProductPrice - totalMembershipPrice)
        );

        System.out.println(sendMoneyInfo);
    }
}
