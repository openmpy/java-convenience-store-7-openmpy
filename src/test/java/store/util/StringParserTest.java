package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        assertThat(promotion.getBuyAmount()).isEqualTo(2);
        assertThat(promotion.getGetAmount()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(promotion.getEndDate()).isEqualTo(LocalDate.of(2024, 12, 31));
    }
}