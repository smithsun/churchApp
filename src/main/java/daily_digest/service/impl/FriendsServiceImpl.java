package daily_digest.service.impl;

import daily_digest.domain.Friends;
import daily_digest.repository.FriendsRepository;
import daily_digest.service.FriendsService;
import daily_digest.service.dto.FriendsDTO;
import daily_digest.service.mapper.FriendsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Friends}.
 */
@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {

    private final Logger log = LoggerFactory.getLogger(FriendsServiceImpl.class);

    private final FriendsRepository friendsRepository;

    private final FriendsMapper friendsMapper;

    public FriendsServiceImpl(FriendsRepository friendsRepository, FriendsMapper friendsMapper) {
        this.friendsRepository = friendsRepository;
        this.friendsMapper = friendsMapper;
    }

    @Override
    public FriendsDTO save(FriendsDTO friendsDTO) {
        log.debug("Request to save Friends : {}", friendsDTO);
        Friends friends = friendsMapper.toEntity(friendsDTO);
        friends = friendsRepository.save(friends);
        return friendsMapper.toDto(friends);
    }

    @Override
    public Optional<FriendsDTO> partialUpdate(FriendsDTO friendsDTO) {
        log.debug("Request to partially update Friends : {}", friendsDTO);

        return friendsRepository
            .findById(friendsDTO.getId())
            .map(
                existingFriends -> {
                    friendsMapper.partialUpdate(existingFriends, friendsDTO);

                    return existingFriends;
                }
            )
            .map(friendsRepository::save)
            .map(friendsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FriendsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendsRepository.findAll(pageable).map(friendsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FriendsDTO> findOne(Long id) {
        log.debug("Request to get Friends : {}", id);
        return friendsRepository.findById(id).map(friendsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Friends : {}", id);
        friendsRepository.deleteById(id);
    }
}
