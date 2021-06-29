package daily_digest.web.rest;

import daily_digest.repository.PublicationRepository;
import daily_digest.service.PublicationService;
import daily_digest.service.dto.PublicationDTO;
import daily_digest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link daily_digest.domain.Publication}.
 */
@RestController
@RequestMapping("/api")
public class PublicationResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    private static final String ENTITY_NAME = "publication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicationService publicationService;

    private final PublicationRepository publicationRepository;

    public PublicationResource(PublicationService publicationService, PublicationRepository publicationRepository) {
        this.publicationService = publicationService;
        this.publicationRepository = publicationRepository;
    }

    /**
     * {@code POST  /publications} : Create a new publication.
     *
     * @param publicationDTO the publicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationDTO, or with status {@code 400 (Bad Request)} if the publication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publications")
    public ResponseEntity<PublicationDTO> createPublication(@Valid @RequestBody PublicationDTO publicationDTO) throws URISyntaxException {
        log.debug("REST request to save Publication : {}", publicationDTO);
        if (publicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new publication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicationDTO result = publicationService.save(publicationDTO);
        return ResponseEntity
            .created(new URI("/api/publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publications/:id} : Updates an existing publication.
     *
     * @param id the id of the publicationDTO to save.
     * @param publicationDTO the publicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationDTO,
     * or with status {@code 400 (Bad Request)} if the publicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publications/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PublicationDTO publicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Publication : {}, {}", id, publicationDTO);
        if (publicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicationDTO result = publicationService.save(publicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /publications/:id} : Partial updates given fields of an existing publication, field will ignore if it is null
     *
     * @param id the id of the publicationDTO to save.
     * @param publicationDTO the publicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationDTO,
     * or with status {@code 400 (Bad Request)} if the publicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/publications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PublicationDTO> partialUpdatePublication(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PublicationDTO publicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Publication partially : {}, {}", id, publicationDTO);
        if (publicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationDTO> result = publicationService.partialUpdate(publicationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /publications} : get all the publications.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publications in body.
     */
    @GetMapping("/publications")
    public ResponseEntity<List<PublicationDTO>> getAllPublications(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("digests-is-null".equals(filter)) {
            log.debug("REST request to get all Publications where digests is null");
            return new ResponseEntity<>(publicationService.findAllWhereDigestsIsNull(), HttpStatus.OK);
        }

        if ("publicnotice-is-null".equals(filter)) {
            log.debug("REST request to get all Publications where publicNotice is null");
            return new ResponseEntity<>(publicationService.findAllWherePublicNoticeIsNull(), HttpStatus.OK);
        }

        if ("videos-is-null".equals(filter)) {
            log.debug("REST request to get all Publications where videos is null");
            return new ResponseEntity<>(publicationService.findAllWhereVideosIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Publications");
        Page<PublicationDTO> page = publicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publications/:id} : get the "id" publication.
     *
     * @param id the id of the publicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publications/{id}")
    public ResponseEntity<PublicationDTO> getPublication(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        Optional<PublicationDTO> publicationDTO = publicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationDTO);
    }

    /**
     * {@code DELETE  /publications/:id} : delete the "id" publication.
     *
     * @param id the id of the publicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publications/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication : {}", id);
        publicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
