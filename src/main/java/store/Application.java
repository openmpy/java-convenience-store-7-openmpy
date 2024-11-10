package store;

import store.controller.StoreController;
import store.domain.Products;
import store.domain.Promotions;
import store.util.FileLoader;

public class Application {

    public static void main(String[] args) {
        final Promotions promotions = FileLoader.loadPromotions();
        final Products products = FileLoader.loadProducts();

        final StoreController storeController = new StoreController();
        storeController.start(promotions, products);
    }
}
