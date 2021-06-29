package daily_digest.service;

import daily_digest.service.dto.PublicNoticeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link daily_digest.domain.PublicNotice}.
 */
public interface PublicNoticeService {
    /**
     * Save a publicNotice.
     *
     * @param publicNoticeDTO the entity to save.
     * @return the persisted entity.
     */
    PublicNoticeDTO save(PublicNoticeDTO publicNoticeDTO);

    /**
     * Partially updates a publicNotice.
     *
     * @param publicNoticeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicNoticeDTO> partialUpdate(PublicNoticeDTO publicNoticeDTO);

    /**
     * Get all the publicNotices.
     *
     * @return the list of entities.
     */
    List<PublicNoticeDTO> findAll();

    /**
     * Get the "id" publicNotice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicNoticeDTO> findOne(Long id);

    /**
     * Delete the "id" publicNotice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
