package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsTest {

    private Product product01;
    private Product product02;
    private Product product03;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        product01 = new Product("콜라", 1000, 0, 10, "2+1");
        product02 = new Product("콜라", 1000, 10, 0, null);
        product03 = new Product("사이다", 1500, 10, 0, null);

        productList = List.of(product01, product02, product03);
    }

    @DisplayName("[통과] 정상적으로 Products 클래스 생성")
    @Test
    void test_products_test_01() {
        // when
        final Products products = new Products(productList);

        // then
        assertThat(products.getProducts()).hasSize(2);
        assertThat(products.getProducts().getFirst().getName()).isEqualTo("콜라");
        assertThat(products.getProducts().getLast().getName()).isEqualTo("사이다");
    }

    @DisplayName("[통과] 상품 목록에서 상품명으로 상품 찾기")
    @Test
    void test_products_test_02() {
        // given
        final Products products = new Products(productList);

        // when
        final Product product = products.findProductsByName("콜라");

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isEqualTo(10);
        assertThat(product.getPromotionQuantity()).isEqualTo(10);
        assertThat(product.getPromotionName()).isEqualTo("2+1");
    }

    @DisplayName("[통과] 상품 목록에서 상품명으로 상품을 찾을 수 없는 경우 null 값 반환")
    @Test
    void test_products_test_03() {
        // given
        final Products products = new Products(productList);

        // when
        final Product product = products.findProductsByName("맥주");

        // then
        assertThat(product).isNull();
    }
}