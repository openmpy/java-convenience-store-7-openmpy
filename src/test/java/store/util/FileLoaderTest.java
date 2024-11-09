package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileLoaderTest {

    @DisplayName("[통과] 정상적으로 promotions.md 불러오기")
    @Test
    void file_loader_test_01() {
        // when
        final List<String> loadPromotions = FileLoader.loadPromotions();

        // then
        assertThat(loadPromotions.getFirst()).isEqualTo("탄산2+1,2,1,2024-01-01,2024-12-31");
        assertThat(loadPromotions.getLast()).isEqualTo("반짝할인,1,1,2024-11-01,2024-11-30");
    }

    @DisplayName("[통과] 정상적으로 products.md 불러오기")
    @Test
    void file_loader_test_02() {
        // when
        final List<String> loadProducts = FileLoader.loadProducts();

        // then
        assertThat(loadProducts.getFirst()).isEqualTo("콜라,1000,10,탄산2+1");
        assertThat(loadProducts.getLast()).isEqualTo("컵라면,1700,10,null");
    }
}