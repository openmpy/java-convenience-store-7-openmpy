package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import store.domain.Cart;
import store.domain.Order;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Receipt;
import store.dto.ProductPurchaseDto;
import store.service.ProductService;
import store.util.FileLoader;
import store.util.StringParser;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    public static void main(String[] args) {
        // 1. 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
        final Promotions promotions = loadPromotionFile();
        final Products products = loadProductFile();

        while (true) {
            // 2. 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다.
            OutputView.printWelcomeMessage();
            OutputView.printProductsInfo(products);

            final Order order = roadOrder(promotions, products);

            // 7. 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
            final Receipt receipt = new Receipt(order);
            receipt.printResult();

            final String input = InputView.readRefreshStore();
            System.out.println();
            if (!input.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    private static Promotions loadPromotionFile() {
        final List<String> loadPromotions = FileLoader.loadPromotions();
        final List<Promotion> promotions = loadPromotions.stream()
                .map(StringParser::parsePromotion)
                .toList();
        return new Promotions(promotions);
    }

    private static Products loadProductFile() {
        final List<String> loadProducts = FileLoader.loadProducts();
        final List<Product> products = loadProducts.stream()
                .map(StringParser::parseProduct)
                .toList();
        return new Products(products);
    }

    private static Order roadOrder(final Promotions promotions, final Products products) {
        while (true) {
            try {
                // 3. 구매할 상품과 수량을 입력 받는 메세지를 출력하고 값을 입력 받는다.
                final String purchaseProductInfo = InputView.readPurchaseProductInfo();
                final ProductService productService = new ProductService();
                final List<ProductPurchaseDto> productPurchaseDtos = productService.addProductPurchase(
                        purchaseProductInfo);

                final Cart cart = new Cart();
                productPurchaseDtos.forEach(it -> cart.addProduct(products, it.productName(), it.quantity()));

                final Order order = new Order(promotions, products, cart);
                final LocalDate nowDate = DateTimes.now().toLocalDate();
                order.checkBonusProduct(nowDate);

                // 6. 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력하고, 입력 받는다.
                order.addMembershipProduct();
                order.checkMembershipDiscount();

                order.validateProductPurchase();

                // 주문 정상
                order.settle();
                return order;
            } catch (final IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
