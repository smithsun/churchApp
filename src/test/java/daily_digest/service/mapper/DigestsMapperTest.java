package daily_digest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DigestsMapperTest {

    private DigestsMapper digestsMapper;

    @BeforeEach
    public void setUp() {
        digestsMapper = new DigestsMapperImpl();
    }
}
