package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

    @DisplayName("[통과] 1+1 행사일 때 증정 상품 개수 확인")
    @ParameterizedTest(name = "입력값: {0}, 기대값: {1}")
    @CsvSource(value = {
            "1, 1",
            "2, 1",
            "3, 1",
            "4, 2",
    })
    void promotion_test_01(final int input, final int expected) {
        // given
        final Promotion promotion = new Promotion("1+1", 1, 1, LocalDate.now(), LocalDate.now().plusDays(1));

        // when
        final int bonusProduct = promotion.getBonusProduct(input);

        // then
        assertThat(bonusProduct).isEqualTo(expected);
    }

    @DisplayName("[통과] 2+1 행사일 때 증정 상품 개수 확인")
    @ParameterizedTest(name = "입력값: {0}, 기대값: {1}")
    @CsvSource(value = {
            "1, 0",
            "2, 1",
            "3, 1",
            "4, 1",
            "5, 1",
            "6, 2",
    })
    void promotion_test_02(final int input, final int expected) {
        // given
        final Promotion promotion = new Promotion("2+1", 2, 1, LocalDate.now(), LocalDate.now().plusDays(1));

        // when
        final int bonusProduct = promotion.getBonusProduct(input);

        // then
        assertThat(bonusProduct).isEqualTo(expected);
    }
}