package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("[통과] 프로모션이 존재하는 Product 클래스 생성")
    @Test
    void product_test_01() {
        // when
        final Product product = new Product("콜라", 1000, 0, 10, "2+1");

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isZero();
        assertThat(product.getPromotionQuantity()).isEqualTo(10);
        assertThat(product.getPromotionName()).isEqualTo("2+1");
        assertThat(product.getTotalQuantity()).isEqualTo(10);
        assertThat(product.getFormattedPrice()).isEqualTo("1,000");
    }

    @DisplayName("[통과] 프로모션이 존재하지 않는 Product 클래스 생성")
    @Test
    void product_test_02() {
        // when
        final Product product = new Product("콜라", 1000, 10, 0, null);

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isEqualTo(10);
        assertThat(product.getPromotionQuantity()).isZero();
        assertThat(product.getPromotionName()).isNull();
        assertThat(product.getTotalQuantity()).isEqualTo(10);
        assertThat(product.getFormattedPrice()).isEqualTo("1,000");
    }
}