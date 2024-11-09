package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringSplitterTest {

    @DisplayName("[통과] 문자열을 콤마(,) 기준으로 분리")
    @Test
    void string_splitter_test_01() {
        // given
        final String string = "1,2,3";

        // when
        final List<String> strings = StringSplitter.splitComma(string);

        // then
        assertThat(strings).hasSize(3);
    }
}