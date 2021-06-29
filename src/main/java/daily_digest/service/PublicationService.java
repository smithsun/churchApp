package daily_digest.service;

import daily_digest.service.dto.PublicationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link daily_digest.domain.Publication}.
 */
public interface PublicationService {
    /**
     * Save a publication.
     *
     * @param publicationDTO the entity to save.
     * @return the persisted entity.
     */
    PublicationDTO save(PublicationDTO publicationDTO);

    /**
     * Partially updates a publication.
     *
     * @param publicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicationDTO> partialUpdate(PublicationDTO publicationDTO);

    /**
     * Get all the publications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicationDTO> findAll(Pageable pageable);
    /**
     * Get all the PublicationDTO where Digests is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PublicationDTO> findAllWhereDigestsIsNull();
    /**
     * Get all the PublicationDTO where PublicNotice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PublicationDTO> findAllWherePublicNoticeIsNull();
    /**
     * Get all the PublicationDTO where Videos is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PublicationDTO> findAllWhereVideosIsNull();

    /**
     * Get the "id" publication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicationDTO> findOne(Long id);

    /**
     * Delete the "id" publication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
