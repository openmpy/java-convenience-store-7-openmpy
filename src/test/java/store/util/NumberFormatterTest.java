package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberFormatterTest {

    @DisplayName("[통과] 숫자 포맷팅 확인")
    @Test
    void number_formatter_test_01() {
        // when
        final String result = NumberFormatter.apply(1000000000);

        // then
        assertThat(result).isEqualTo("1,000,000,000");
    }
}