package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.PublicNoticeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicNotice} and its DTO {@link PublicNoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = { PublicationMapper.class })
public interface PublicNoticeMapper extends EntityMapper<PublicNoticeDTO, PublicNotice> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "id")
    PublicNoticeDTO toDto(PublicNotice s);
}
