package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

class StringParserTest {

    @DisplayName("[통과] 문자열을 입력 받아 Promotion 클래스로 변환")
    @Test
    void string_parser_test_01() {
        // given
        final String string = "탄산2+1,2,1,2024-01-01,2024-12-31";

        // when
        final Promotion promotion = StringParser.parsePromotion(string);

        // then
        assertThat(promotion.getName()).isEqualTo("탄산2+1");
        assertThat(promotion.getBuyQuantity()).isEqualTo(2);
        assertThat(promotion.getGetQuantity()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(promotion.getEndDate()).isEqualTo(LocalDate.of(2024, 12, 31));
    }

    @DisplayName("[통과] 문자열을 입력 받아 프로모션이 존재하는 Product 클래스로 변환")
    @Test
    void string_parser_test_02() {
        // given
        final String string = "콜라,1000,10,탄산2+1";

        // when
        final Product product = StringParser.parseProduct(string);

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isZero();
        assertThat(product.getPromotionQuantity()).isEqualTo(10);
        assertThat(product.getPromotionName()).isEqualTo("탄산2+1");
    }

    @DisplayName("[통과] 문자열을 입력 받아 프로모션이 존재하지 않는 Product 클래스로 변환")
    @Test
    void string_parser_test_03() {
        // given
        final String string = "콜라,1000,10,null";

        // when
        final Product product = StringParser.parseProduct(string);

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getDefaultQuantity()).isEqualTo(10);
        assertThat(product.getPromotionQuantity()).isZero();
        assertThat(product.getPromotionName()).isNull();
    }
}