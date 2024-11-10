package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    private Promotions promotions;
    private Products products;
    private Cart cart;

    @BeforeEach
    void setUp() {
        final Promotion promotion01 = new Promotion("1+1", 1, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        final Promotion promotion02 = new Promotion("2+1", 2, 1, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 31));
        promotions = new Promotions(List.of(promotion01, promotion02));

        final Product product01 = new Product("콜라", 1000, 0, 10, "2+1");
        final Product product02 = new Product("맥주", 1000, 10, 0, null);
        final Product product03 = new Product("사이다", 1500, 10, 10, "1+1");
        products = new Products(List.of(product01, product02, product03));

        cart = new Cart();
    }

    @DisplayName("[통과] 1+1 할인 혜택에 적용하는 상품을 구매")
    @Test
    void order_test_01() {
        // given
        cart.addProduct(products, "사이다", 10);
        final Order order = new Order(promotions, products, cart);

        final Product lastProduct = products.getProducts().getLast();

        // when
        order.checkBonusProduct(LocalDate.of(2024, 6, 1));

        // then
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getBonusItems()).hasSize(1);
        assertThat(order.getBonusItems().get(lastProduct)).isEqualTo(5);
    }

    @DisplayName("[통과] 2+1 할인 혜택에 적용하는 상품을 구매")
    @Test
    void order_test_02() {
        // given
        cart.addProduct(products, "콜라", 10);
        final Order order = new Order(promotions, products, cart);

        final Product firstProduct = products.getProducts().getFirst();

        // when
        order.checkBonusProduct(LocalDate.of(2024, 12, 1));

        // then
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getBonusItems()).hasSize(1);
        assertThat(order.getBonusItems().get(firstProduct)).isEqualTo(3);
    }

    @DisplayName("[통과] 멤버십 할인 적용되는 상품 구매")
    @Test
    void order_test_03() {
        // given
        cart.addProduct(products, "맥주", 10);
        final Order order = new Order(promotions, products, cart);

        final Product product = products.getProducts().get(1);

        // when
        order.addMembershipProduct();

        // then
        assertThat(order.getMembershipItems()).hasSize(1);
        assertThat(order.getMembershipItems().get(product)).isEqualTo(10);
    }

    @DisplayName("[통과] 프로모션 기간이 지난 상품을 구매")
    @Test
    void order_test_04() {
        // given
        cart.addProduct(products, "콜라", 10);
        final Order order = new Order(promotions, products, cart);

        // when
        order.checkBonusProduct(LocalDate.of(2024, 6, 1));

        // then
        assertThat(cart.getItems()).hasSize(1);
    }
}