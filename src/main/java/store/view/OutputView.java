package store.view;

import store.domain.Product;
import store.domain.Products;

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
}
