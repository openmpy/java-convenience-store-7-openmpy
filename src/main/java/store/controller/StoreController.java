package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import store.domain.Cart;
import store.domain.Order;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Receipt;
import store.dto.ProductPurchaseDto;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    public void start(final Promotions promotions, final Products products) {
        do {
            displayWelcomeMessage(products);
            final Order order = processOrder(promotions, products);
            printReceipt(order);
        } while (shouldRefreshStore());
    }

    private void displayWelcomeMessage(final Products products) {
        OutputView.printWelcomeMessage();
        OutputView.printProductsInfo(products);
    }

    private Order processOrder(final Promotions promotions, final Products products) {
        while (true) {
            try {
                final List<ProductPurchaseDto> productPurchaseDtos = getProductPurchaseInfo();
                final Cart cart = createCart(products, productPurchaseDtos);
                return createOrder(promotions, products, cart);
            } catch (final IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    private List<ProductPurchaseDto> getProductPurchaseInfo() {
        final String purchaseProductInfo = InputView.readPurchaseProductInfo();
        final ProductService productService = new ProductService();
        return productService.addProductPurchase(purchaseProductInfo);
    }

    private Cart createCart(final Products products, final List<ProductPurchaseDto> productPurchaseDtos) {
        final Cart cart = new Cart();
        productPurchaseDtos.forEach(it -> cart.addProduct(products, it.productName(), it.quantity()));
        return cart;
    }

    private Order createOrder(final Promotions promotions, final Products products, final Cart cart) {
        final Order order = new Order(promotions, products, cart);
        final LocalDate nowDate = DateTimes.now().toLocalDate();

        order.checkBonusProduct(nowDate);
        order.addMembershipProduct();
        order.checkMembershipDiscount();
        order.validateProductPurchase();
        order.settle();
        return order;
    }

    private void printReceipt(final Order order) {
        final Receipt receipt = new Receipt(order);
        receipt.printResult();
    }

    private boolean shouldRefreshStore() {
        final String input = InputView.readRefreshStore();
        System.out.println();
        return input.equalsIgnoreCase("Y");
    }
}

