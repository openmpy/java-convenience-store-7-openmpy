package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.dto.ProductPurchaseDto;
import store.exception.InvalidPurchaseException;

class ProductServiceTest {

    @DisplayName("[통과] 상품 구매 입력값을 분리해서 ProductPurchaseDto 타입으로 반환")
    @Test
    void product_service_test_01() {
        // given
        final ProductService productService = new ProductService();

        // when
        final List<ProductPurchaseDto> productPurchaseDtos = productService.addProductPurchase("[콜라-3],[사이다-1]");

        // then
        assertThat(productPurchaseDtos).hasSize(2);
        assertThat(productPurchaseDtos.getFirst().productName()).isEqualTo("콜라");
        assertThat(productPurchaseDtos.getFirst().quantity()).isEqualTo(3);
        assertThat(productPurchaseDtos.getLast().productName()).isEqualTo("사이다");
        assertThat(productPurchaseDtos.getLast().quantity()).isEqualTo(1);
    }

    @DisplayName("[예외] 올바르지 않은 입력값을 입력 했을 경우")
    @ParameterizedTest(name = "입력값: {0}")
    @ValueSource(strings = {"콜라-3,사이다-1", "<콜라-3>,<사이다-1>", "[[콜라-3],[사이다-1]]"})
    void 예외_product_service_test_01(final String input) {
        // given
        final ProductService productService = new ProductService();

        // when & then
        assertThatThrownBy(() -> productService.addProductPurchase(input))
                .isInstanceOf(InvalidPurchaseException.class);
    }
}