package daily_digest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyVersesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyVerses.class);
        DailyVerses dailyVerses1 = new DailyVerses();
        dailyVerses1.setId(1L);
        DailyVerses dailyVerses2 = new DailyVerses();
        dailyVerses2.setId(dailyVerses1.getId());
        assertThat(dailyVerses1).isEqualTo(dailyVerses2);
        dailyVerses2.setId(2L);
        assertThat(dailyVerses1).isNotEqualTo(dailyVerses2);
        dailyVerses1.setId(null);
        assertThat(dailyVerses1).isNotEqualTo(dailyVerses2);
    }
}
