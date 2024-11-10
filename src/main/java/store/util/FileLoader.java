package store.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;

public class FileLoader {

    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final int SKIP_LINES_COUNT = 1;

    public static Promotions loadPromotions() {
        final List<Promotion> promotions = loadFile(PROMOTIONS_FILE_PATH).stream()
                .map(StringParser::parsePromotion)
                .toList();
        return new Promotions(promotions);
    }

    public static Products loadProducts() {
        final List<Product> products = loadFile(PRODUCTS_FILE_PATH).stream()
                .map(StringParser::parseProduct)
                .toList();
        return new Products(products);
    }

    private static List<String> loadFile(final String filePath) {
        final Path path = Paths.get(filePath);

        try (final Stream<String> lines = Files.lines(path)) {
            return lines.skip(SKIP_LINES_COUNT).toList();
        } catch (final IOException e) {
            final String error = String.format("[ERROR] %s 파일을 불러올 수 없습니다.", filePath);
            throw new IllegalArgumentException(error);
        }
    }
}
