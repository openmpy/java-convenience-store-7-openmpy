package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;

class FileLoaderTest {

    @DisplayName("[통과] 정상적으로 promotions.md 불러오기")
    @Test
    void file_loader_test_01() {
        // when
        final Promotions promotions = FileLoader.loadPromotions();

        // then
        assertThat(promotions.getPromotions()).hasSize(3);
    }

    @DisplayName("[통과] 정상적으로 products.md 불러오기")
    @Test
    void file_loader_test_02() {
        // when
        final Products products = FileLoader.loadProducts();

        // then
        assertThat(products.getProducts()).hasSize(11);
    }
}