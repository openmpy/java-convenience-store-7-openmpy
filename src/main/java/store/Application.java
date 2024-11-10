package store;

import java.util.List;
import store.controller.StoreController;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.util.FileLoader;
import store.util.StringParser;

public class Application {

    public static void main(String[] args) {
        final Promotions promotions = loadPromotionFile();
        final Products products = loadProductFile();

        final StoreController storeController = new StoreController();
        storeController.start(promotions, products);
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
}
