package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.NotFoundProductException;

class CartTest {

    private Product product01;
    private Product product02;
    private Product product03;
    private Products products;

    @BeforeEach
    void setUp() {
        product01 = new Product("콜라", 1000, 0, 10, "2+1");
        product02 = new Product("콜라", 1000, 10, 0, null);
        product03 = new Product("사이다", 1500, 10, 0, null);
        products = new Products(List.of(product01, product02, product03));
    }

    @DisplayName("[통과] 장바구니에 상품 목록에 있는 상품 넣기")
    @Test
    void cart_test_01() {
        // given
        final Cart cart = new Cart();

        // when
        cart.addProduct(products, "콜라", 10);
        cart.addProduct(products, "사이다", 10);

        // then
        assertThat(cart.getItems()).hasSize(2);
    }

    @DisplayName("[예외] 장바구니에 상품 목록에 있지 않는 상품 넣기")
    @Test
    void 예외_cart_test_01() {
        // given
        final Cart cart = new Cart();

        // when & then
        assertThatThrownBy(() -> cart.addProduct(products, "맥주", 10))
                .isInstanceOf(NotFoundProductException.class);
    }
}