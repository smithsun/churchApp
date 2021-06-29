package daily_digest.web.rest;

import static daily_digest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.Videos;
import daily_digest.repository.VideosRepository;
import daily_digest.service.dto.VideosDTO;
import daily_digest.service.mapper.VideosMapper;
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
 * Integration tests for the {@link VideosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideosResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_WORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORDS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VideosRepository videosRepository;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideosMockMvc;

    private Videos videos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videos createEntity(EntityManager em) {
        Videos videos = new Videos()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .notes(DEFAULT_NOTES)
            .keyWords(DEFAULT_KEY_WORDS)
            .lastUpdateBy(DEFAULT_LAST_UPDATE_BY)
            .status(DEFAULT_STATUS)
            .eventDate(DEFAULT_EVENT_DATE);
        return videos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videos createUpdatedEntity(EntityManager em) {
        Videos videos = new Videos()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .notes(UPDATED_NOTES)
            .keyWords(UPDATED_KEY_WORDS)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        return videos;
    }

    @BeforeEach
    public void initTest() {
        videos = createEntity(em);
    }

    @Test
    @Transactional
    void createVideos() throws Exception {
        int databaseSizeBeforeCreate = videosRepository.findAll().size();
        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);
        restVideosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videosDTO)))
            .andExpect(status().isCreated());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeCreate + 1);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVideos.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVideos.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testVideos.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testVideos.getKeyWords()).isEqualTo(DEFAULT_KEY_WORDS);
        assertThat(testVideos.getLastUpdateBy()).isEqualTo(DEFAULT_LAST_UPDATE_BY);
        assertThat(testVideos.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVideos.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    void createVideosWithExistingId() throws Exception {
        // Create the Videos with an existing ID
        videos.setId(1L);
        VideosDTO videosDTO = videosMapper.toDto(videos);

        int databaseSizeBeforeCreate = videosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get all the videosList
        restVideosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videos.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].keyWords").value(hasItem(DEFAULT_KEY_WORDS)))
            .andExpect(jsonPath("$.[*].lastUpdateBy").value(hasItem(DEFAULT_LAST_UPDATE_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))));
    }

    @Test
    @Transactional
    void getVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        // Get the videos
        restVideosMockMvc
            .perform(get(ENTITY_API_URL_ID, videos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videos.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.keyWords").value(DEFAULT_KEY_WORDS))
            .andExpect(jsonPath("$.lastUpdateBy").value(DEFAULT_LAST_UPDATE_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.eventDate").value(sameInstant(DEFAULT_EVENT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingVideos() throws Exception {
        // Get the videos
        restVideosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos
        Videos updatedVideos = videosRepository.findById(videos.getId()).get();
        // Disconnect from session so that the updates on updatedVideos are not directly saved in db
        em.detach(updatedVideos);
        updatedVideos
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .notes(UPDATED_NOTES)
            .keyWords(UPDATED_KEY_WORDS)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);
        VideosDTO videosDTO = videosMapper.toDto(updatedVideos);

        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideos.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVideos.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testVideos.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testVideos.getKeyWords()).isEqualTo(UPDATED_KEY_WORDS);
        assertThat(testVideos.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testVideos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVideos.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideosWithPatch() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos using partial update
        Videos partialUpdatedVideos = new Videos();
        partialUpdatedVideos.setId(videos.getId());

        partialUpdatedVideos
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .keyWords(UPDATED_KEY_WORDS)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);

        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideos))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideos.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVideos.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testVideos.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testVideos.getKeyWords()).isEqualTo(UPDATED_KEY_WORDS);
        assertThat(testVideos.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testVideos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVideos.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateVideosWithPatch() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeUpdate = videosRepository.findAll().size();

        // Update the videos using partial update
        Videos partialUpdatedVideos = new Videos();
        partialUpdatedVideos.setId(videos.getId());

        partialUpdatedVideos
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .notes(UPDATED_NOTES)
            .keyWords(UPDATED_KEY_WORDS)
            .lastUpdateBy(UPDATED_LAST_UPDATE_BY)
            .status(UPDATED_STATUS)
            .eventDate(UPDATED_EVENT_DATE);

        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideos))
            )
            .andExpect(status().isOk());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
        Videos testVideos = videosList.get(videosList.size() - 1);
        assertThat(testVideos.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideos.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVideos.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testVideos.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testVideos.getKeyWords()).isEqualTo(UPDATED_KEY_WORDS);
        assertThat(testVideos.getLastUpdateBy()).isEqualTo(UPDATED_LAST_UPDATE_BY);
        assertThat(testVideos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVideos.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideos() throws Exception {
        int databaseSizeBeforeUpdate = videosRepository.findAll().size();
        videos.setId(count.incrementAndGet());

        // Create the Videos
        VideosDTO videosDTO = videosMapper.toDto(videos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(videosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Videos in the database
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideos() throws Exception {
        // Initialize the database
        videosRepository.saveAndFlush(videos);

        int databaseSizeBeforeDelete = videosRepository.findAll().size();

        // Delete the videos
        restVideosMockMvc
            .perform(delete(ENTITY_API_URL_ID, videos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Videos> videosList = videosRepository.findAll();
        assertThat(videosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
