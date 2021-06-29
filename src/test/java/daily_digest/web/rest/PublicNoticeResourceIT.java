package daily_digest.web.rest;

import static daily_digest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.PublicNotice;
import daily_digest.domain.enumeration.NoticeType;
import daily_digest.repository.PublicNoticeRepository;
import daily_digest.service.dto.PublicNoticeDTO;
import daily_digest.service.mapper.PublicNoticeMapper;
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
 * Integration tests for the {@link PublicNoticeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicNoticeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final NoticeType DEFAULT_TYPE = NoticeType.TRAINING;
    private static final NoticeType UPDATED_TYPE = NoticeType.PRAYER;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/public-notices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicNoticeRepository publicNoticeRepository;

    @Autowired
    private PublicNoticeMapper publicNoticeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicNoticeMockMvc;

    private PublicNotice publicNotice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicNotice createEntity(EntityManager em) {
        PublicNotice publicNotice = new PublicNotice()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .lastUpdateBy(DEFAULT_LAST_UPDATE_BY)
            .status(DEFAULT_STATUS)
            .eventDate(DEFAULT_EVENT_DATE);
        return publicNotice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicNotice createUpdatedEntity(EntityManager em) {
        PublicNotice publicNotice = new PublicNotice()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        return publicNotice;
    }

    @BeforeEach
    public void initTest() {
        publicNotice = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicNotice() throws Exception {
        int databaseSizeBeforeCreate = publicNoticeRepository.findAll().size();
        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);
        restPublicNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeCreate + 1);
        PublicNotice testPublicNotice = publicNoticeList.get(publicNoticeList.size() - 1);
        assertThat(testPublicNotice.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPublicNotice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPublicNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPublicNotice.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testPublicNotice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPublicNotice.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    void createPublicNoticeWithExistingId() throws Exception {
        // Create the PublicNotice with an existing ID
        publicNotice.setId(1L);
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        int databaseSizeBeforeCreate = publicNoticeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicNoticeRepository.findAll().size();
        // set the field null
        publicNotice.setTitle(null);

        // Create the PublicNotice, which fails.
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        restPublicNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicNoticeRepository.findAll().size();
        // set the field null
        publicNotice.setType(null);

        // Create the PublicNotice, which fails.
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        restPublicNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicNoticeRepository.findAll().size();
        // set the field null
        publicNotice.setContent(null);

        // Create the PublicNotice, which fails.
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        restPublicNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPublicNotices() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        // Get all the publicNoticeList
        restPublicNoticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicNotice.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].lastUpdateBy").value(hasItem(DEFAULT_LAST_UPDATE_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))));
    }

    @Test
    @Transactional
    void getPublicNotice() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        // Get the publicNotice
        restPublicNoticeMockMvc
            .perform(get(ENTITY_API_URL_ID, publicNotice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicNotice.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.lastUpdateBy").value(DEFAULT_LAST_UPDATE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.eventDate").value(sameInstant(DEFAULT_EVENT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingPublicNotice() throws Exception {
        // Get the publicNotice
        restPublicNoticeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicNotice() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();

        // Update the publicNotice
        PublicNotice updatedPublicNotice = publicNoticeRepository.findById(publicNotice.getId()).get();
        // Disconnect from session so that the updates on updatedPublicNotice are not directly saved in db
        em.detach(updatedPublicNotice);
        updatedPublicNotice
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(updatedPublicNotice);

        restPublicNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicNoticeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
        PublicNotice testPublicNotice = publicNoticeList.get(publicNoticeList.size() - 1);
        assertThat(testPublicNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPublicNotice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPublicNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicNotice.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testPublicNotice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicNotice.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicNoticeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicNoticeWithPatch() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();

        // Update the publicNotice using partial update
        PublicNotice partialUpdatedPublicNotice = new PublicNotice();
        partialUpdatedPublicNotice.setId(publicNotice.getId());

        partialUpdatedPublicNotice
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS);

        restPublicNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicNotice))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
        PublicNotice testPublicNotice = publicNoticeList.get(publicNoticeList.size() - 1);
        assertThat(testPublicNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPublicNotice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPublicNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicNotice.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testPublicNotice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicNotice.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePublicNoticeWithPatch() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();

        // Update the publicNotice using partial update
        PublicNotice partialUpdatedPublicNotice = new PublicNotice();
        partialUpdatedPublicNotice.setId(publicNotice.getId());

        partialUpdatedPublicNotice
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);

        restPublicNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicNotice))
            )
            .andExpect(status().isOk());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
        PublicNotice testPublicNotice = publicNoticeList.get(publicNoticeList.size() - 1);
        assertThat(testPublicNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPublicNotice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPublicNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicNotice.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testPublicNotice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicNotice.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicNoticeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicNotice() throws Exception {
        int databaseSizeBeforeUpdate = publicNoticeRepository.findAll().size();
        publicNotice.setId(count.incrementAndGet());

        // Create the PublicNotice
        PublicNoticeDTO publicNoticeDTO = publicNoticeMapper.toDto(publicNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicNoticeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicNotice in the database
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicNotice() throws Exception {
        // Initialize the database
        publicNoticeRepository.saveAndFlush(publicNotice);

        int databaseSizeBeforeDelete = publicNoticeRepository.findAll().size();

        // Delete the publicNotice
        restPublicNoticeMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicNotice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicNotice> publicNoticeList = publicNoticeRepository.findAll();
        assertThat(publicNoticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
