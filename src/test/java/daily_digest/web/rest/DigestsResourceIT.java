package daily_digest.web.rest;

import static daily_digest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.Digests;
import daily_digest.domain.enumeration.DigestType;
import daily_digest.repository.DigestsRepository;
import daily_digest.service.dto.DigestsDTO;
import daily_digest.service.mapper.DigestsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DigestsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DigestsResourceIT {

    private static final DigestType DEFAULT_TYPE = DigestType.NEWBELIVER;
    private static final DigestType UPDATED_TYPE = DigestType.SERVICEONE;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_VERSE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_VERSE = "BBBBBBBBBB";

    private static final String DEFAULT_PRAY_READ_VERSE = "AAAAAAAAAA";
    private static final String UPDATED_PRAY_READ_VERSE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/digests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DigestsRepository digestsRepository;

    @Autowired
    private DigestsMapper digestsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDigestsMockMvc;

    private Digests digests;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Digests createEntity(EntityManager em) {
        Digests digests = new Digests()
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .imgVerse(DEFAULT_IMG_VERSE)
            .prayReadVerse(DEFAULT_PRAY_READ_VERSE)
            .content(DEFAULT_CONTENT)
            .lastUpdateBy(DEFAULT_LAST_UPDATE_BY)
            .status(DEFAULT_STATUS)
            .eventDate(DEFAULT_EVENT_DATE);
        return digests;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Digests createUpdatedEntity(EntityManager em) {
        Digests digests = new Digests()
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .imgVerse(UPDATED_IMG_VERSE)
            .prayReadVerse(UPDATED_PRAY_READ_VERSE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        return digests;
    }

    @BeforeEach
    public void initTest() {
        digests = createEntity(em);
    }

    @Test
    @Transactional
    void createDigests() throws Exception {
        int databaseSizeBeforeCreate = digestsRepository.findAll().size();
        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);
        restDigestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(digestsDTO)))
            .andExpect(status().isCreated());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeCreate + 1);
        Digests testDigests = digestsList.get(digestsList.size() - 1);
        assertThat(testDigests.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDigests.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDigests.getImgVerse()).isEqualTo(DEFAULT_IMG_VERSE);
        assertThat(testDigests.getPrayReadVerse()).isEqualTo(DEFAULT_PRAY_READ_VERSE);
        assertThat(testDigests.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDigests.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testDigests.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDigests.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    void createDigestsWithExistingId() throws Exception {
        // Create the Digests with an existing ID
        digests.setId(1L);
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        int databaseSizeBeforeCreate = digestsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDigestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(digestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = digestsRepository.findAll().size();
        // set the field null
        digests.setType(null);

        // Create the Digests, which fails.
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        restDigestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(digestsDTO)))
            .andExpect(status().isBadRequest());

        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = digestsRepository.findAll().size();
        // set the field null
        digests.setTitle(null);

        // Create the Digests, which fails.
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        restDigestsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(digestsDTO)))
            .andExpect(status().isBadRequest());

        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDigests() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        // Get all the digestsList
        restDigestsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(digests.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].imgVerse").value(hasItem(DEFAULT_IMG_VERSE)))
            .andExpect(jsonPath("$.[*].prayReadVerse").value(hasItem(DEFAULT_PRAY_READ_VERSE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateBy").value(hasItem(DEFAULT_LAST_UPDATE_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))));
    }

    @Test
    @Transactional
    void getDigests() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        // Get the digests
        restDigestsMockMvc
            .perform(get(ENTITY_API_URL_ID, digests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(digests.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.imgVerse").value(DEFAULT_IMG_VERSE))
            .andExpect(jsonPath("$.prayReadVerse").value(DEFAULT_PRAY_READ_VERSE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.lastUpdateBy").value(DEFAULT_LAST_UPDATE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.eventDate").value(sameInstant(DEFAULT_EVENT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingDigests() throws Exception {
        // Get the digests
        restDigestsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDigests() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();

        // Update the digests
        Digests updatedDigests = digestsRepository.findById(digests.getId()).get();
        // Disconnect from session so that the updates on updatedDigests are not directly saved in db
        em.detach(updatedDigests);
        updatedDigests
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .imgVerse(UPDATED_IMG_VERSE)
            .prayReadVerse(UPDATED_PRAY_READ_VERSE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        DigestsDTO digestsDTO = digestsMapper.toDto(updatedDigests);

        restDigestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, digestsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
        Digests testDigests = digestsList.get(digestsList.size() - 1);
        assertThat(testDigests.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDigests.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDigests.getImgVerse()).isEqualTo(UPDATED_IMG_VERSE);
        assertThat(testDigests.getPrayReadVerse()).isEqualTo(UPDATED_PRAY_READ_VERSE);
        assertThat(testDigests.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDigests.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testDigests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDigests.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, digestsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(digestsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDigestsWithPatch() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();

        // Update the digests using partial update
        Digests partialUpdatedDigests = new Digests();
        partialUpdatedDigests.setId(digests.getId());

        partialUpdatedDigests.title(UPDATED_TITLE).prayReadVerse(UPDATED_PRAY_READ_VERSE).status(UPDATED_STATUS);

        restDigestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDigests.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDigests))
            )
            .andExpect(status().isOk());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
        Digests testDigests = digestsList.get(digestsList.size() - 1);
        assertThat(testDigests.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDigests.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDigests.getImgVerse()).isEqualTo(DEFAULT_IMG_VERSE);
        assertThat(testDigests.getPrayReadVerse()).isEqualTo(UPDATED_PRAY_READ_VERSE);
        assertThat(testDigests.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDigests.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testDigests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDigests.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDigestsWithPatch() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();

        // Update the digests using partial update
        Digests partialUpdatedDigests = new Digests();
        partialUpdatedDigests.setId(digests.getId());

        partialUpdatedDigests
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .imgVerse(UPDATED_IMG_VERSE)
            .prayReadVerse(UPDATED_PRAY_READ_VERSE)
            .content(UPDATED_CONTENT)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);

        restDigestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDigests.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDigests))
            )
            .andExpect(status().isOk());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
        Digests testDigests = digestsList.get(digestsList.size() - 1);
        assertThat(testDigests.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDigests.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDigests.getImgVerse()).isEqualTo(UPDATED_IMG_VERSE);
        assertThat(testDigests.getPrayReadVerse()).isEqualTo(UPDATED_PRAY_READ_VERSE);
        assertThat(testDigests.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDigests.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testDigests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDigests.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, digestsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDigests() throws Exception {
        int databaseSizeBeforeUpdate = digestsRepository.findAll().size();
        digests.setId(count.incrementAndGet());

        // Create the Digests
        DigestsDTO digestsDTO = digestsMapper.toDto(digests);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigestsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(digestsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Digests in the database
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDigests() throws Exception {
        // Initialize the database
        digestsRepository.saveAndFlush(digests);

        int databaseSizeBeforeDelete = digestsRepository.findAll().size();

        // Delete the digests
        restDigestsMockMvc
            .perform(delete(ENTITY_API_URL_ID, digests.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Digests> digestsList = digestsRepository.findAll();
        assertThat(digestsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
