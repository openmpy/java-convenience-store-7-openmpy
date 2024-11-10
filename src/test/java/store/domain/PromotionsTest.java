package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionsTest {

    private List<Promotion> promotionList;

    @BeforeEach
    void setUp() {
        Promotion promotion01 = new Promotion("1+1", 1, 1, LocalDate.now(), LocalDate.now().plusDays(1));
        Promotion promotion02 = new Promotion("2+1", 2, 1, LocalDate.now(), LocalDate.now().plusDays(1));
        promotionList = List.of(promotion01, promotion02);
    }

    @DisplayName("[통과] 정상적으로 Promotions 클래스 생성")
    @Test
    void promotions_test_01() {
        // when
        final Promotions promotions = new Promotions(promotionList);

        // then
        assertThat(promotions.getPromotions()).hasSize(2);
    }

    @DisplayName("[통과] 프로모션 목록에서 프로모션 명으로 프로모션 찾기")
    @Test
    void promotions_test_02() {
        // given
        final Promotions promotions = new Promotions(promotionList);

        // when
        final Promotion promotion = promotions.findPromotionsByName("1+1");

        // then
        assertThat(promotion.getName()).isEqualTo("1+1");
        assertThat(promotion.getBuyQuantity()).isEqualTo(1);
        assertThat(promotion.getGetQuantity()).isEqualTo(1);
    }

    @DisplayName("[통과] 프로모션 목록에서 프로모션 명으로 프로모션을 찾지 못 할 경우 null 값 반환")
    @Test
    void promotions_test_03() {
        // given
        final Promotions promotions = new Promotions(promotionList);

        // when
        final Promotion promotion = promotions.findPromotionsByName("3+1");

        // then
        assertThat(promotion).isNull();
    }
}