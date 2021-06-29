package daily_digest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import daily_digest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideosDTO.class);
        VideosDTO videosDTO1 = new VideosDTO();
        videosDTO1.setId(1L);
        VideosDTO videosDTO2 = new VideosDTO();
        assertThat(videosDTO1).isNotEqualTo(videosDTO2);
        videosDTO2.setId(videosDTO1.getId());
        assertThat(videosDTO1).isEqualTo(videosDTO2);
        videosDTO2.setId(2L);
        assertThat(videosDTO1).isNotEqualTo(videosDTO2);
        videosDTO1.setId(null);
        assertThat(videosDTO1).isNotEqualTo(videosDTO2);
    }
}
