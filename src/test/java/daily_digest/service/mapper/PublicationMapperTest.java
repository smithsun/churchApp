package daily_digest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationMapperTest {

    private PublicationMapper publicationMapper;

    @BeforeEach
    public void setUp() {
        publicationMapper = new PublicationMapperImpl();
    }
}
