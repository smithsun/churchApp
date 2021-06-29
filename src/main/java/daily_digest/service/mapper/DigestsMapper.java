package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.DigestsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Digests} and its DTO {@link DigestsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PublicationMapper.class })
public interface DigestsMapper extends EntityMapper<DigestsDTO, Digests> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "id")
    DigestsDTO toDto(Digests s);
}
