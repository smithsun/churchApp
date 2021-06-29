package daily_digest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicNoticeMapperTest {

    private PublicNoticeMapper publicNoticeMapper;

    @BeforeEach
    public void setUp() {
        publicNoticeMapper = new PublicNoticeMapperImpl();
    }
}
