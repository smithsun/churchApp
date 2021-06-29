package daily_digest.web.rest;

import static daily_digest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.Publication;
import daily_digest.repository.PublicationRepository;
import daily_digest.service.dto.PublicationDTO;
import daily_digest.service.mapper.PublicationMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PublicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_ORG_SIGNATURE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/publications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationMockMvc;

    private Publication publication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publication createEntity(EntityManager em) {
        Publication publication = new Publication()
            .type(DEFAULT_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .priority(DEFAULT_PRIORITY)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdateBy(DEFAULT_LAST_UPDATE_BY)
            .orgSignature(DEFAULT_ORG_SIGNATURE);
        return publication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publication createUpdatedEntity(EntityManager em) {
        Publication publication = new Publication()
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .orgSignature(UPDATED_ORG_SIGNATURE);
        return publication;
    }

    @BeforeEach
    public void initTest() {
        publication = createEntity(em);
    }

    @Test
    @Transactional
    void createPublication() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();
        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);
        restPublicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeCreate + 1);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPublication.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPublication.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPublication.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPublication.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPublication.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testPublication.getOrgSignature()).isEqualTo(DEFAULT_ORG_SIGNATURE);
    }

    @Test
    @Transactional
    void createPublicationWithExistingId() throws Exception {
        // Create the Publication with an existing ID
        publication.setId(1L);
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setType(null);

        // Create the Publication, which fails.
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        restPublicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPublications() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get all the publicationList
        restPublicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdateBy").value(hasItem(DEFAULT_LAST_UPDATE_BY)))
            .andExpect(jsonPath("$.[*].orgSignature").value(hasItem(DEFAULT_ORG_SIGNATURE)));
    }

    @Test
    @Transactional
    void getPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc
            .perform(get(ENTITY_API_URL_ID, publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publication.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastUpdateBy").value(DEFAULT_LAST_UPDATE_BY))
            .andExpect(jsonPath("$.orgSignature").value(DEFAULT_ORG_SIGNATURE));
    }

    @Test
    @Transactional
    void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication
        Publication updatedPublication = publicationRepository.findById(publication.getId()).get();
        // Disconnect from session so that the updates on updatedPublication are not directly saved in db
        em.detach(updatedPublication);
        updatedPublication
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .orgSignature(UPDATED_ORG_SIGNATURE);
        PublicationDTO publicationDTO = publicationMapper.toDto(updatedPublication);

        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPublication.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPublication.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPublication.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPublication.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPublication.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testPublication.getOrgSignature()).isEqualTo(UPDATED_ORG_SIGNATURE);
    }

    @Test
    @Transactional
    void putNonExistingPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationWithPatch() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication using partial update
        Publication partialUpdatedPublication = new Publication();
        partialUpdatedPublication.setId(publication.getId());

        partialUpdatedPublication.orgSignature(UPDATED_ORG_SIGNATURE);

        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublication))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPublication.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPublication.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPublication.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPublication.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPublication.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testPublication.getOrgSignature()).isEqualTo(UPDATED_ORG_SIGNATURE);
    }

    @Test
    @Transactional
    void fullUpdatePublicationWithPatch() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication using partial update
        Publication partialUpdatedPublication = new Publication();
        partialUpdatedPublication.setId(publication.getId());

        partialUpdatedPublication
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .orgSignature(UPDATED_ORG_SIGNATURE);

        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublication))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publicationList.get(publicationList.size() - 1);
        assertThat(testPublication.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPublication.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPublication.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPublication.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPublication.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPublication.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testPublication.getOrgSignature()).isEqualTo(UPDATED_ORG_SIGNATURE);
    }

    @Test
    @Transactional
    void patchNonExistingPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublication() throws Exception {
        int databaseSizeBeforeUpdate = publicationRepository.findAll().size();
        publication.setId(count.incrementAndGet());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publication in the database
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        int databaseSizeBeforeDelete = publicationRepository.findAll().size();

        // Delete the publication
        restPublicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, publication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publication> publicationList = publicationRepository.findAll();
        assertThat(publicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
