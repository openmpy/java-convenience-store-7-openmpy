package store.domain;

import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = products;
    }

    public Product findProductsByName(final String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProducts() {
        return products;
    }
}
