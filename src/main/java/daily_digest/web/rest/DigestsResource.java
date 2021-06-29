package daily_digest.web.rest;

import daily_digest.repository.DigestsRepository;
import daily_digest.service.DigestsService;
import daily_digest.service.dto.DigestsDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link daily_digest.domain.Digests}.
 */
@RestController
@RequestMapping("/api")
public class DigestsResource {

    private final Logger log = LoggerFactory.getLogger(DigestsResource.class);

    private static final String ENTITY_NAME = "digests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DigestsService digestsService;

    private final DigestsRepository digestsRepository;

    public DigestsResource(DigestsService digestsService, DigestsRepository digestsRepository) {
        this.digestsService = digestsService;
        this.digestsRepository = digestsRepository;
    }

    /**
     * {@code POST  /digests} : Create a new digests.
     *
     * @param digestsDTO the digestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new digestsDTO, or with status {@code 400 (Bad Request)} if the digests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/digests")
    public ResponseEntity<DigestsDTO> createDigests(@Valid @RequestBody DigestsDTO digestsDTO) throws URISyntaxException {
        log.debug("REST request to save Digests : {}", digestsDTO);
        if (digestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new digests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DigestsDTO result = digestsService.save(digestsDTO);
        return ResponseEntity
            .created(new URI("/api/digests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /digests/:id} : Updates an existing digests.
     *
     * @param id the id of the digestsDTO to save.
     * @param digestsDTO the digestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated digestsDTO,
     * or with status {@code 400 (Bad Request)} if the digestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the digestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/digests/{id}")
    public ResponseEntity<DigestsDTO> updateDigests(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DigestsDTO digestsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Digests : {}, {}", id, digestsDTO);
        if (digestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, digestsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!digestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DigestsDTO result = digestsService.save(digestsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, digestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /digests/:id} : Partial updates given fields of an existing digests, field will ignore if it is null
     *
     * @param id the id of the digestsDTO to save.
     * @param digestsDTO the digestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated digestsDTO,
     * or with status {@code 400 (Bad Request)} if the digestsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the digestsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the digestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/digests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DigestsDTO> partialUpdateDigests(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DigestsDTO digestsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Digests partially : {}, {}", id, digestsDTO);
        if (digestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, digestsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!digestsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DigestsDTO> result = digestsService.partialUpdate(digestsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, digestsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /digests} : get all the digests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of digests in body.
     */
    @GetMapping("/digests")
    public ResponseEntity<List<DigestsDTO>> getAllDigests(Pageable pageable) {
        log.debug("REST request to get a page of Digests");
        Page<DigestsDTO> page = digestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /digests/:id} : get the "id" digests.
     *
     * @param id the id of the digestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the digestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/digests/{id}")
    public ResponseEntity<DigestsDTO> getDigests(@PathVariable Long id) {
        log.debug("REST request to get Digests : {}", id);
        Optional<DigestsDTO> digestsDTO = digestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(digestsDTO);
    }

    /**
     * {@code DELETE  /digests/:id} : delete the "id" digests.
     *
     * @param id the id of the digestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/digests/{id}")
    public ResponseEntity<Void> deleteDigests(@PathVariable Long id) {
        log.debug("REST request to delete Digests : {}", id);
        digestsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
