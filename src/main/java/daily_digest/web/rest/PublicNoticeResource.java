package daily_digest.web.rest;

import daily_digest.repository.PublicNoticeRepository;
import daily_digest.service.PublicNoticeService;
import daily_digest.service.dto.PublicNoticeDTO;
import daily_digest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link daily_digest.domain.PublicNotice}.
 */
@RestController
@RequestMapping("/api")
public class PublicNoticeResource {

    private final Logger log = LoggerFactory.getLogger(PublicNoticeResource.class);

    private static final String ENTITY_NAME = "publicNotice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicNoticeService publicNoticeService;

    private final PublicNoticeRepository publicNoticeRepository;

    public PublicNoticeResource(PublicNoticeService publicNoticeService, PublicNoticeRepository publicNoticeRepository) {
        this.publicNoticeService = publicNoticeService;
        this.publicNoticeRepository = publicNoticeRepository;
    }

    /**
     * {@code POST  /public-notices} : Create a new publicNotice.
     *
     * @param publicNoticeDTO the publicNoticeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicNoticeDTO, or with status {@code 400 (Bad Request)} if the publicNotice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-notices")
    public ResponseEntity<PublicNoticeDTO> createPublicNotice(@Valid @RequestBody PublicNoticeDTO publicNoticeDTO)
        throws URISyntaxException {
        log.debug("REST request to save PublicNotice : {}", publicNoticeDTO);
        if (publicNoticeDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicNotice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicNoticeDTO result = publicNoticeService.save(publicNoticeDTO);
        return ResponseEntity
            .created(new URI("/api/public-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-notices/:id} : Updates an existing publicNotice.
     *
     * @param id the id of the publicNoticeDTO to save.
     * @param publicNoticeDTO the publicNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the publicNoticeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-notices/{id}")
    public ResponseEntity<PublicNoticeDTO> updatePublicNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PublicNoticeDTO publicNoticeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PublicNotice : {}, {}", id, publicNoticeDTO);
        if (publicNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicNoticeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicNoticeDTO result = publicNoticeService.save(publicNoticeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicNoticeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /public-notices/:id} : Partial updates given fields of an existing publicNotice, field will ignore if it is null
     *
     * @param id the id of the publicNoticeDTO to save.
     * @param publicNoticeDTO the publicNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the publicNoticeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicNoticeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/public-notices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PublicNoticeDTO> partialUpdatePublicNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PublicNoticeDTO publicNoticeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PublicNotice partially : {}, {}", id, publicNoticeDTO);
        if (publicNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicNoticeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicNoticeDTO> result = publicNoticeService.partialUpdate(publicNoticeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicNoticeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /public-notices} : get all the publicNotices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicNotices in body.
     */
    @GetMapping("/public-notices")
    public List<PublicNoticeDTO> getAllPublicNotices() {
        log.debug("REST request to get all PublicNotices");
        return publicNoticeService.findAll();
    }

    /**
     * {@code GET  /public-notices/:id} : get the "id" publicNotice.
     *
     * @param id the id of the publicNoticeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicNoticeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-notices/{id}")
    public ResponseEntity<PublicNoticeDTO> getPublicNotice(@PathVariable Long id) {
        log.debug("REST request to get PublicNotice : {}", id);
        Optional<PublicNoticeDTO> publicNoticeDTO = publicNoticeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicNoticeDTO);
    }

    /**
     * {@code DELETE  /public-notices/:id} : delete the "id" publicNotice.
     *
     * @param id the id of the publicNoticeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-notices/{id}")
    public ResponseEntity<Void> deletePublicNotice(@PathVariable Long id) {
        log.debug("REST request to delete PublicNotice : {}", id);
        publicNoticeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
