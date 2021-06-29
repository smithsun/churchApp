package daily_digest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DigestsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DigestsDTO.class);
        DigestsDTO digestsDTO1 = new DigestsDTO();
        digestsDTO1.setId(1L);
        DigestsDTO digestsDTO2 = new DigestsDTO();
        assertThat(digestsDTO1).isNotEqualTo(digestsDTO2);
        digestsDTO2.setId(digestsDTO1.getId());
        assertThat(digestsDTO1).isEqualTo(digestsDTO2);
        digestsDTO2.setId(2L);
        assertThat(digestsDTO1).isNotEqualTo(digestsDTO2);
        digestsDTO1.setId(null);
        assertThat(digestsDTO1).isNotEqualTo(digestsDTO2);
    }
}
