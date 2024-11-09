package store.view;

import java.util.List;
import store.domain.Product;

public class OutputView {

    public static void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public static void printProductsInfo(final List<Product> products) {
        for (final Product product : products) {
            final String formatted = String.format("- %s %s원 %s %s",
                    product.getName(),
                    product.getFormattedPrice(),
                    product.getQuantityState(product.getCustomQuantity()),
                    product.getCustomPromotionName()
            );
            System.out.println(formatted);
        }
        System.out.println();
    }
}
