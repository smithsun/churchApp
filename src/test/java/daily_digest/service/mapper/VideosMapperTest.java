package daily_digest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideosMapperTest {

    private VideosMapper videosMapper;

    @BeforeEach
    public void setUp() {
        videosMapper = new VideosMapperImpl();
    }
}
