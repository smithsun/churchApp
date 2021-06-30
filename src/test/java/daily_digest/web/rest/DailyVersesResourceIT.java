package daily_digest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.DailyVerses;
import daily_digest.repository.DailyVersesRepository;
import daily_digest.service.dto.DailyVersesDTO;
import daily_digest.service.mapper.DailyVersesMapper;
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
 * Integration tests for the {@link DailyVersesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyVersesResourceIT {

    private static final String DEFAULT_VERSES = "AAAAAAAAAA";
    private static final String UPDATED_VERSES = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER = 1L;
    private static final Long UPDATED_ORDER = 2L;

    private static final String ENTITY_API_URL = "/api/daily-verses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyVersesRepository dailyVersesRepository;

    @Autowired
    private DailyVersesMapper dailyVersesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyVersesMockMvc;

    private DailyVerses dailyVerses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyVerses createEntity(EntityManager em) {
        DailyVerses dailyVerses = new DailyVerses().verses(DEFAULT_VERSES).order(DEFAULT_ORDER);
        return dailyVerses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyVerses createUpdatedEntity(EntityManager em) {
        DailyVerses dailyVerses = new DailyVerses().verses(UPDATED_VERSES).order(UPDATED_ORDER);
        return dailyVerses;
    }

    @BeforeEach
    public void initTest() {
        dailyVerses = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyVerses() throws Exception {
        int databaseSizeBeforeCreate = dailyVersesRepository.findAll().size();
        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);
        restDailyVersesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeCreate + 1);
        DailyVerses testDailyVerses = dailyVersesList.get(dailyVersesList.size() - 1);
        assertThat(testDailyVerses.getVerses()).isEqualTo(DEFAULT_VERSES);
        assertThat(testDailyVerses.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    void createDailyVersesWithExistingId() throws Exception {
        // Create the DailyVerses with an existing ID
        dailyVerses.setId(1L);
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        int databaseSizeBeforeCreate = dailyVersesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyVersesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDailyVerses() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        // Get all the dailyVersesList
        restDailyVersesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyVerses.getId().intValue())))
            .andExpect(jsonPath("$.[*].verses").value(hasItem(DEFAULT_VERSES)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    void getDailyVerses() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        // Get the dailyVerses
        restDailyVersesMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyVerses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyVerses.getId().intValue()))
            .andExpect(jsonPath("$.verses").value(DEFAULT_VERSES))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDailyVerses() throws Exception {
        // Get the dailyVerses
        restDailyVersesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDailyVerses() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();

        // Update the dailyVerses
        DailyVerses updatedDailyVerses = dailyVersesRepository.findById(dailyVerses.getId()).get();
        // Disconnect from session so that the updates on updatedDailyVerses are not directly saved in db
        em.detach(updatedDailyVerses);
        updatedDailyVerses.verses(UPDATED_VERSES).order(UPDATED_ORDER);
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(updatedDailyVerses);

        restDailyVersesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyVersesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isOk());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
        DailyVerses testDailyVerses = dailyVersesList.get(dailyVersesList.size() - 1);
        assertThat(testDailyVerses.getVerses()).isEqualTo(UPDATED_VERSES);
        assertThat(testDailyVerses.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void putNonExistingDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyVersesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyVersesWithPatch() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();

        // Update the dailyVerses using partial update
        DailyVerses partialUpdatedDailyVerses = new DailyVerses();
        partialUpdatedDailyVerses.setId(dailyVerses.getId());

        partialUpdatedDailyVerses.order(UPDATED_ORDER);

        restDailyVersesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyVerses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyVerses))
            )
            .andExpect(status().isOk());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
        DailyVerses testDailyVerses = dailyVersesList.get(dailyVersesList.size() - 1);
        assertThat(testDailyVerses.getVerses()).isEqualTo(DEFAULT_VERSES);
        assertThat(testDailyVerses.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateDailyVersesWithPatch() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();

        // Update the dailyVerses using partial update
        DailyVerses partialUpdatedDailyVerses = new DailyVerses();
        partialUpdatedDailyVerses.setId(dailyVerses.getId());

        partialUpdatedDailyVerses.verses(UPDATED_VERSES).order(UPDATED_ORDER);

        restDailyVersesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyVerses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyVerses))
            )
            .andExpect(status().isOk());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
        DailyVerses testDailyVerses = dailyVersesList.get(dailyVersesList.size() - 1);
        assertThat(testDailyVerses.getVerses()).isEqualTo(UPDATED_VERSES);
        assertThat(testDailyVerses.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyVersesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyVerses() throws Exception {
        int databaseSizeBeforeUpdate = dailyVersesRepository.findAll().size();
        dailyVerses.setId(count.incrementAndGet());

        // Create the DailyVerses
        DailyVersesDTO dailyVersesDTO = dailyVersesMapper.toDto(dailyVerses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyVersesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dailyVersesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyVerses in the database
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyVerses() throws Exception {
        // Initialize the database
        dailyVersesRepository.saveAndFlush(dailyVerses);

        int databaseSizeBeforeDelete = dailyVersesRepository.findAll().size();

        // Delete the dailyVerses
        restDailyVersesMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyVerses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyVerses> dailyVersesList = dailyVersesRepository.findAll();
        assertThat(dailyVersesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
