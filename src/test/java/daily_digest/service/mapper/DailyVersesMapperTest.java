package daily_digest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DailyVersesMapperTest {

    private DailyVersesMapper dailyVersesMapper;

    @BeforeEach
    public void setUp() {
        dailyVersesMapper = new DailyVersesMapperImpl();
    }
}
