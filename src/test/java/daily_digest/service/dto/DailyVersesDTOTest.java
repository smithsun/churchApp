package daily_digest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyVersesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyVersesDTO.class);
        DailyVersesDTO dailyVersesDTO1 = new DailyVersesDTO();
        dailyVersesDTO1.setId(1L);
        DailyVersesDTO dailyVersesDTO2 = new DailyVersesDTO();
        assertThat(dailyVersesDTO1).isNotEqualTo(dailyVersesDTO2);
        dailyVersesDTO2.setId(dailyVersesDTO1.getId());
        assertThat(dailyVersesDTO1).isEqualTo(dailyVersesDTO2);
        dailyVersesDTO2.setId(2L);
        assertThat(dailyVersesDTO1).isNotEqualTo(dailyVersesDTO2);
        dailyVersesDTO1.setId(null);
        assertThat(dailyVersesDTO1).isNotEqualTo(dailyVersesDTO2);
    }
}
