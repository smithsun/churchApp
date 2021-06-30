package daily_digest.service;

import daily_digest.service.dto.DailyVersesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link daily_digest.domain.DailyVerses}.
 */
public interface DailyVersesService {
    /**
     * Save a dailyVerses.
     *
     * @param dailyVersesDTO the entity to save.
     * @return the persisted entity.
     */
    DailyVersesDTO save(DailyVersesDTO dailyVersesDTO);

    /**
     * Partially updates a dailyVerses.
     *
     * @param dailyVersesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DailyVersesDTO> partialUpdate(DailyVersesDTO dailyVersesDTO);

    /**
     * Get all the dailyVerses.
     *
     * @return the list of entities.
     */
    List<DailyVersesDTO> findAll();

    /**
     * Get the "id" dailyVerses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DailyVersesDTO> findOne(Long id);

    /**
     * Delete the "id" dailyVerses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
