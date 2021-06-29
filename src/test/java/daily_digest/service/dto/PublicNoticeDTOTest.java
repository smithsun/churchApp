package daily_digest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicNoticeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicNoticeDTO.class);
        PublicNoticeDTO publicNoticeDTO1 = new PublicNoticeDTO();
        publicNoticeDTO1.setId(1L);
        PublicNoticeDTO publicNoticeDTO2 = new PublicNoticeDTO();
        assertThat(publicNoticeDTO1).isNotEqualTo(publicNoticeDTO2);
        publicNoticeDTO2.setId(publicNoticeDTO1.getId());
        assertThat(publicNoticeDTO1).isEqualTo(publicNoticeDTO2);
        publicNoticeDTO2.setId(2L);
        assertThat(publicNoticeDTO1).isNotEqualTo(publicNoticeDTO2);
        publicNoticeDTO1.setId(null);
        assertThat(publicNoticeDTO1).isNotEqualTo(publicNoticeDTO2);
    }
}
