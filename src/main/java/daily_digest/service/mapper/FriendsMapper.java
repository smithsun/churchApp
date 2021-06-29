package daily_digest.service.mapper;

import daily_digest.domain.*;
import daily_digest.service.dto.FriendsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Friends} and its DTO {@link FriendsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FriendsMapper extends EntityMapper<FriendsDTO, Friends> {}
