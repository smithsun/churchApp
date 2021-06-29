package daily_digest.service;

import daily_digest.service.dto.FriendsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link daily_digest.domain.Friends}.
 */
public interface FriendsService {
    /**
     * Save a friends.
     *
     * @param friendsDTO the entity to save.
     * @return the persisted entity.
     */
    FriendsDTO save(FriendsDTO friendsDTO);

    /**
     * Partially updates a friends.
     *
     * @param friendsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FriendsDTO> partialUpdate(FriendsDTO friendsDTO);

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FriendsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" friends.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FriendsDTO> findOne(Long id);

    /**
     * Delete the "id" friends.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
