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
        final List<String> loadPromotions = FileLoader.loadPromotions();
        final List<String> loadProducts = FileLoader.loadProducts();

        final List<Promotion> promotionList = loadPromotions.stream()
                .map(StringParser::parsePromotion)
                .toList();
        final Promotions promotions = new Promotions(promotionList);

        final List<Product> productList = loadProducts.stream()
                .map(StringParser::parseProduct)
                .toList();
        final Products products = new Products(productList);

        // 2. 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다.
        OutputView.printWelcomeMessage();
        OutputView.printProductsInfo(products);

        final Order order = roadOrder(promotions, products);

        // 7. 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
        final Receipt receipt = new Receipt(order);
        receipt.printResult();
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

                // 4. 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 혜택에 대한 안내 메세지를 출력하고, 그 수량만큼 추가 여부를 입력 받는다.
                order.checkGetBonusProduct(nowDate);

                // 5. 프로모션 재고가 부족하여 일부 수량을 포로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메세지를 출력하고, 입력 받는다.
                order.checkNotApplyBonusProduct(nowDate);

                // 6. 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력하고, 입력 받는다.
                order.addMembershipProduct();
                order.checkMembershipDiscount();

                order.validateProductPurchase();
                return order;
            } catch (final IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
