package daily_digest.service;

import daily_digest.service.dto.DigestsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link daily_digest.domain.Digests}.
 */
public interface DigestsService {
    /**
     * Save a digests.
     *
     * @param digestsDTO the entity to save.
     * @return the persisted entity.
     */
    DigestsDTO save(DigestsDTO digestsDTO);

    /**
     * Partially updates a digests.
     *
     * @param digestsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DigestsDTO> partialUpdate(DigestsDTO digestsDTO);

    /**
     * Get all the digests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DigestsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" digests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DigestsDTO> findOne(Long id);

    /**
     * Delete the "id" digests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
