package store.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import store.exception.InvalidInputException;
import store.exception.NotFoundProductException;

public class Cart {

    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public void addProduct(final Products products, final String productName, final int quantity) {
        final Product product = products.findProductsByName(productName);
        if (product == null) {
            throw new NotFoundProductException();
        }
        if (quantity <= 0) {
            throw new InvalidInputException();
        }

        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}
