package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.PublicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publication} and its DTO {@link PublicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicationMapper extends EntityMapper<PublicationDTO, Publication> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublicationDTO toDtoId(Publication publication);
}
