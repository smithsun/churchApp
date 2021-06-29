package daily_digest.web.rest;

import daily_digest.repository.VideosRepository;
import daily_digest.service.VideosService;
import daily_digest.service.dto.VideosDTO;
import daily_digest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link daily_digest.domain.Videos}.
 */
@RestController
@RequestMapping("/api")
public class VideosResource {

    private final Logger log = LoggerFactory.getLogger(VideosResource.class);

    private static final String ENTITY_NAME = "videos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideosService videosService;

    private final VideosRepository videosRepository;

    public VideosResource(VideosService videosService, VideosRepository videosRepository) {
        this.videosService = videosService;
        this.videosRepository = videosRepository;
    }

    /**
     * {@code POST  /videos} : Create a new videos.
     *
     * @param videosDTO the videosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videosDTO, or with status {@code 400 (Bad Request)} if the videos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/videos")
    public ResponseEntity<VideosDTO> createVideos(@RequestBody VideosDTO videosDTO) throws URISyntaxException {
        log.debug("REST request to save Videos : {}", videosDTO);
        if (videosDTO.getId() != null) {
            throw new BadRequestAlertException("A new videos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideosDTO result = videosService.save(videosDTO);
        return ResponseEntity
            .created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /videos/:id} : Updates an existing videos.
     *
     * @param id the id of the videosDTO to save.
     * @param videosDTO the videosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videosDTO,
     * or with status {@code 400 (Bad Request)} if the videosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/videos/{id}")
    public ResponseEntity<VideosDTO> updateVideos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VideosDTO videosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Videos : {}, {}", id, videosDTO);
        if (videosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VideosDTO result = videosService.save(videosDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /videos/:id} : Partial updates given fields of an existing videos, field will ignore if it is null
     *
     * @param id the id of the videosDTO to save.
     * @param videosDTO the videosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videosDTO,
     * or with status {@code 400 (Bad Request)} if the videosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the videosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the videosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/videos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VideosDTO> partialUpdateVideos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VideosDTO videosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Videos partially : {}, {}", id, videosDTO);
        if (videosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VideosDTO> result = videosService.partialUpdate(videosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /videos} : get all the videos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videos in body.
     */
    @GetMapping("/videos")
    public List<VideosDTO> getAllVideos() {
        log.debug("REST request to get all Videos");
        return videosService.findAll();
    }

    /**
     * {@code GET  /videos/:id} : get the "id" videos.
     *
     * @param id the id of the videosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<VideosDTO> getVideos(@PathVariable Long id) {
        log.debug("REST request to get Videos : {}", id);
        Optional<VideosDTO> videosDTO = videosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videosDTO);
    }

    /**
     * {@code DELETE  /videos/:id} : delete the "id" videos.
     *
     * @param id the id of the videosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideos(@PathVariable Long id) {
        log.debug("REST request to delete Videos : {}", id);
        videosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
