package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.VideosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Videos} and its DTO {@link VideosDTO}.
 */
@Mapper(componentModel = "spring", uses = { PublicationMapper.class })
public interface VideosMapper extends EntityMapper<VideosDTO, Videos> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "id")
    VideosDTO toDto(Videos s);
}
