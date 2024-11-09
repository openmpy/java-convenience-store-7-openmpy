package store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public void addProduct(final Products products, final String productName, final int quantity) {
        final Product product = products.findProductsByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}
