package daily_digest.web.rest;

import static daily_digest.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import daily_digest.IntegrationTest;
import daily_digest.domain.Friends;
import daily_digest.repository.FriendsRepository;
import daily_digest.service.dto.FriendsDTO;
import daily_digest.service.mapper.FriendsMapper;
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
 * Integration tests for the {@link FriendsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FriendsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_USED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_USED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_INTIMACY_ORDER = 1L;
    private static final Long UPDATED_INTIMACY_ORDER = 2L;

    private static final String ENTITY_API_URL = "/api/friends";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendsMockMvc;

    private Friends friends;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friends createEntity(EntityManager em) {
        Friends friends = new Friends()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .cellPhone(DEFAULT_CELL_PHONE)
            .lastUsedTime(DEFAULT_LAST_USED_TIME)
            .intimacyOrder(DEFAULT_INTIMACY_ORDER);
        return friends;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friends createUpdatedEntity(EntityManager em) {
        Friends friends = new Friends()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .cellPhone(UPDATED_CELL_PHONE)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .intimacyOrder(UPDATED_INTIMACY_ORDER);
        return friends;
    }

    @BeforeEach
    public void initTest() {
        friends = createEntity(em);
    }

    @Test
    @Transactional
    void createFriends() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();
        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);
        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isCreated());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate + 1);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFriends.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFriends.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testFriends.getLastUsedTime()).isEqualTo(DEFAULT_LAST_USED_TIME);
        assertThat(testFriends.getIntimacyOrder()).isEqualTo(DEFAULT_INTIMACY_ORDER);
    }

    @Test
    @Transactional
    void createFriendsWithExistingId() throws Exception {
        // Create the Friends with an existing ID
        friends.setId(1L);
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendsRepository.findAll().size();
        // set the field null
        friends.setName(null);

        // Create the Friends, which fails.
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = friendsRepository.findAll().size();
        // set the field null
        friends.setLastName(null);

        // Create the Friends, which fails.
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get all the friendsList
        restFriendsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friends.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].lastUsedTime").value(hasItem(sameInstant(DEFAULT_LAST_USED_TIME))))
            .andExpect(jsonPath("$.[*].intimacyOrder").value(hasItem(DEFAULT_INTIMACY_ORDER.intValue())));
    }

    @Test
    @Transactional
    void getFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get the friends
        restFriendsMockMvc
            .perform(get(ENTITY_API_URL_ID, friends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friends.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE))
            .andExpect(jsonPath("$.lastUsedTime").value(sameInstant(DEFAULT_LAST_USED_TIME)))
            .andExpect(jsonPath("$.intimacyOrder").value(DEFAULT_INTIMACY_ORDER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFriends() throws Exception {
        // Get the friends
        restFriendsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends
        Friends updatedFriends = friendsRepository.findById(friends.getId()).get();
        // Disconnect from session so that the updates on updatedFriends are not directly saved in db
        em.detach(updatedFriends);
        updatedFriends
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .cellPhone(UPDATED_CELL_PHONE)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .intimacyOrder(UPDATED_INTIMACY_ORDER);
        FriendsDTO friendsDTO = friendsMapper.toDto(updatedFriends);

        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFriends.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFriends.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testFriends.getLastUsedTime()).isEqualTo(UPDATED_LAST_USED_TIME);
        assertThat(testFriends.getIntimacyOrder()).isEqualTo(UPDATED_INTIMACY_ORDER);
    }

    @Test
    @Transactional
    void putNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFriendsWithPatch() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends using partial update
        Friends partialUpdatedFriends = new Friends();
        partialUpdatedFriends.setId(friends.getId());

        partialUpdatedFriends.name(UPDATED_NAME).cellPhone(UPDATED_CELL_PHONE);

        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriends.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriends))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFriends.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFriends.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testFriends.getLastUsedTime()).isEqualTo(DEFAULT_LAST_USED_TIME);
        assertThat(testFriends.getIntimacyOrder()).isEqualTo(DEFAULT_INTIMACY_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateFriendsWithPatch() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends using partial update
        Friends partialUpdatedFriends = new Friends();
        partialUpdatedFriends.setId(friends.getId());

        partialUpdatedFriends
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .cellPhone(UPDATED_CELL_PHONE)
            .lastUsedTime(UPDATED_LAST_USED_TIME)
            .intimacyOrder(UPDATED_INTIMACY_ORDER);

        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriends.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriends))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFriends.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFriends.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testFriends.getLastUsedTime()).isEqualTo(UPDATED_LAST_USED_TIME);
        assertThat(testFriends.getIntimacyOrder()).isEqualTo(UPDATED_INTIMACY_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeDelete = friendsRepository.findAll().size();

        // Delete the friends
        restFriendsMockMvc
            .perform(delete(ENTITY_API_URL_ID, friends.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
