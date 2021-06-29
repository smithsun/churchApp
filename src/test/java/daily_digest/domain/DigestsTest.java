package daily_digest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DigestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Digests.class);
        Digests digests1 = new Digests();
        digests1.setId(1L);
        Digests digests2 = new Digests();
        digests2.setId(digests1.getId());
        assertThat(digests1).isEqualTo(digests2);
        digests2.setId(2L);
        assertThat(digests1).isNotEqualTo(digests2);
        digests1.setId(null);
        assertThat(digests1).isNotEqualTo(digests2);
    }
}
