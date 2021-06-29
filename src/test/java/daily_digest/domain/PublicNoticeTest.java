package daily_digest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicNoticeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicNotice.class);
        PublicNotice publicNotice1 = new PublicNotice();
        publicNotice1.setId(1L);
        PublicNotice publicNotice2 = new PublicNotice();
        publicNotice2.setId(publicNotice1.getId());
        assertThat(publicNotice1).isEqualTo(publicNotice2);
        publicNotice2.setId(2L);
        assertThat(publicNotice1).isNotEqualTo(publicNotice2);
        publicNotice1.setId(null);
        assertThat(publicNotice1).isNotEqualTo(publicNotice2);
    }
}
