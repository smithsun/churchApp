package daily_digest.web.rest;

import daily_digest.repository.FriendsRepository;
import daily_digest.service.FriendsService;
import daily_digest.service.dto.FriendsDTO;
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
 * REST controller for managing {@link daily_digest.domain.Friends}.
 */
@RestController
@RequestMapping("/api")
public class FriendsResource {

    private final Logger log = LoggerFactory.getLogger(FriendsResource.class);

    private static final String ENTITY_NAME = "friends";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendsService friendsService;

    private final FriendsRepository friendsRepository;

    public FriendsResource(FriendsService friendsService, FriendsRepository friendsRepository) {
        this.friendsService = friendsService;
        this.friendsRepository = friendsRepository;
    }

    /**
     * {@code POST  /friends} : Create a new friends.
     *
     * @param friendsDTO the friendsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendsDTO, or with status {@code 400 (Bad Request)} if the friends has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friends")
    public ResponseEntity<FriendsDTO> createFriends(@Valid @RequestBody FriendsDTO friendsDTO) throws URISyntaxException {
        log.debug("REST request to save Friends : {}", friendsDTO);
        if (friendsDTO.getId() != null) {
            throw new BadRequestAlertException("A new friends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendsDTO result = friendsService.save(friendsDTO);
        return ResponseEntity
            .created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friends/:id} : Updates an existing friends.
     *
     * @param id the id of the friendsDTO to save.
     * @param friendsDTO the friendsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendsDTO,
     * or with status {@code 400 (Bad Request)} if the friendsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friends/{id}")
    public ResponseEntity<FriendsDTO> updateFriends(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FriendsDTO friendsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Friends : {}, {}", id, friendsDTO);
        if (friendsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FriendsDTO result = friendsService.save(friendsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /friends/:id} : Partial updates given fields of an existing friends, field will ignore if it is null
     *
     * @param id the id of the friendsDTO to save.
     * @param friendsDTO the friendsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendsDTO,
     * or with status {@code 400 (Bad Request)} if the friendsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the friendsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the friendsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/friends/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FriendsDTO> partialUpdateFriends(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FriendsDTO friendsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Friends partially : {}, {}", id, friendsDTO);
        if (friendsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FriendsDTO> result = friendsService.partialUpdate(friendsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /friends} : get all the friends.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friends in body.
     */
    @GetMapping("/friends")
    public ResponseEntity<List<FriendsDTO>> getAllFriends(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<FriendsDTO> page = friendsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /friends/:id} : get the "id" friends.
     *
     * @param id the id of the friendsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friends/{id}")
    public ResponseEntity<FriendsDTO> getFriends(@PathVariable Long id) {
        log.debug("REST request to get Friends : {}", id);
        Optional<FriendsDTO> friendsDTO = friendsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendsDTO);
    }

    /**
     * {@code DELETE  /friends/:id} : delete the "id" friends.
     *
     * @param id the id of the friendsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friends/{id}")
    public ResponseEntity<Void> deleteFriends(@PathVariable Long id) {
        log.debug("REST request to delete Friends : {}", id);
        friendsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
