package store.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Products {

    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = updateProducts(products);
    }

    public Product findProductsByName(final String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private List<Product> updateProducts(final List<Product> products) {
        final Map<String, Product> productMap = new LinkedHashMap<>();
        for (final Product product : products) {
            if (!productMap.containsKey(product.getName())) {
                productMap.put(product.getName(), product);
                continue;
            }
            final Product existingProduct = productMap.get(product.getName());
            final Product newProduct = updateProduct(product, existingProduct);
            productMap.put(product.getName(), newProduct);
        }
        return new ArrayList<>(productMap.values());
    }

    private static Product updateProduct(final Product product, final Product existingProduct) {
        final int newDefaultQuantity = product.getDefaultQuantity() + existingProduct.getDefaultQuantity();
        final int newPromotionQuantity = product.getPromotionQuantity() + existingProduct.getPromotionQuantity();
        String newPromotionName = existingProduct.getPromotionName();

        if (product.getPromotionName() != null) {
            newPromotionName = product.getPromotionName();
        }
        return new Product(product.getName(), product.getPrice(), newDefaultQuantity, newPromotionQuantity,
                newPromotionName);
    }

    public List<Product> getProducts() {
        return products;
    }
}
