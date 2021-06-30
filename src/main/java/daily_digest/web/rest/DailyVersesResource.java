package daily_digest.web.rest;

import daily_digest.repository.DailyVersesRepository;
import daily_digest.service.DailyVersesService;
import daily_digest.service.dto.DailyVersesDTO;
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
 * REST controller for managing {@link daily_digest.domain.DailyVerses}.
 */
@RestController
@RequestMapping("/api")
public class DailyVersesResource {

    private final Logger log = LoggerFactory.getLogger(DailyVersesResource.class);

    private static final String ENTITY_NAME = "dailyVerses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyVersesService dailyVersesService;

    private final DailyVersesRepository dailyVersesRepository;

    public DailyVersesResource(DailyVersesService dailyVersesService, DailyVersesRepository dailyVersesRepository) {
        this.dailyVersesService = dailyVersesService;
        this.dailyVersesRepository = dailyVersesRepository;
    }

    /**
     * {@code POST  /daily-verses} : Create a new dailyVerses.
     *
     * @param dailyVersesDTO the dailyVersesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyVersesDTO, or with status {@code 400 (Bad Request)} if the dailyVerses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-verses")
    public ResponseEntity<DailyVersesDTO> createDailyVerses(@RequestBody DailyVersesDTO dailyVersesDTO) throws URISyntaxException {
        log.debug("REST request to save DailyVerses : {}", dailyVersesDTO);
        if (dailyVersesDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyVerses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyVersesDTO result = dailyVersesService.save(dailyVersesDTO);
        return ResponseEntity
            .created(new URI("/api/daily-verses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-verses/:id} : Updates an existing dailyVerses.
     *
     * @param id the id of the dailyVersesDTO to save.
     * @param dailyVersesDTO the dailyVersesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyVersesDTO,
     * or with status {@code 400 (Bad Request)} if the dailyVersesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyVersesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-verses/{id}")
    public ResponseEntity<DailyVersesDTO> updateDailyVerses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DailyVersesDTO dailyVersesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DailyVerses : {}, {}", id, dailyVersesDTO);
        if (dailyVersesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyVersesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyVersesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyVersesDTO result = dailyVersesService.save(dailyVersesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyVersesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-verses/:id} : Partial updates given fields of an existing dailyVerses, field will ignore if it is null
     *
     * @param id the id of the dailyVersesDTO to save.
     * @param dailyVersesDTO the dailyVersesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyVersesDTO,
     * or with status {@code 400 (Bad Request)} if the dailyVersesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dailyVersesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyVersesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/daily-verses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DailyVersesDTO> partialUpdateDailyVerses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DailyVersesDTO dailyVersesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyVerses partially : {}, {}", id, dailyVersesDTO);
        if (dailyVersesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyVersesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyVersesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyVersesDTO> result = dailyVersesService.partialUpdate(dailyVersesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyVersesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-verses} : get all the dailyVerses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyVerses in body.
     */
    @GetMapping("/daily-verses")
    public List<DailyVersesDTO> getAllDailyVerses() {
        log.debug("REST request to get all DailyVerses");
        return dailyVersesService.findAll();
    }

    /**
     * {@code GET  /daily-verses/:id} : get the "id" dailyVerses.
     *
     * @param id the id of the dailyVersesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyVersesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-verses/{id}")
    public ResponseEntity<DailyVersesDTO> getDailyVerses(@PathVariable Long id) {
        log.debug("REST request to get DailyVerses : {}", id);
        Optional<DailyVersesDTO> dailyVersesDTO = dailyVersesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyVersesDTO);
    }

    /**
     * {@code DELETE  /daily-verses/:id} : delete the "id" dailyVerses.
     *
     * @param id the id of the dailyVersesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-verses/{id}")
    public ResponseEntity<Void> deleteDailyVerses(@PathVariable Long id) {
        log.debug("REST request to delete DailyVerses : {}", id);
        dailyVersesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
