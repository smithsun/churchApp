package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.DailyVersesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DailyVerses} and its DTO {@link DailyVersesDTO}.
 */
@Mapper(componentModel = "spring", uses = { DigestsMapper.class })
public interface DailyVersesMapper extends EntityMapper<DailyVersesDTO, DailyVerses> {
    @Mapping(target = "digests", source = "digests", qualifiedByName = "id")
    DailyVersesDTO toDto(DailyVerses s);
}
