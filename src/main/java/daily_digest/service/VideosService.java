package daily_digest.service;

import daily_digest.service.dto.VideosDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link daily_digest.domain.Videos}.
 */
public interface VideosService {
    /**
     * Save a videos.
     *
     * @param videosDTO the entity to save.
     * @return the persisted entity.
     */
    VideosDTO save(VideosDTO videosDTO);

    /**
     * Partially updates a videos.
     *
     * @param videosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VideosDTO> partialUpdate(VideosDTO videosDTO);

    /**
     * Get all the videos.
     *
     * @return the list of entities.
     */
    List<VideosDTO> findAll();

    /**
     * Get the "id" videos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideosDTO> findOne(Long id);

    /**
     * Delete the "id" videos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
