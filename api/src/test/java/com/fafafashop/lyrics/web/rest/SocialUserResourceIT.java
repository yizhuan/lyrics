package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.LyricsApp;
import com.fafafashop.lyrics.domain.SocialUser;
import com.fafafashop.lyrics.repository.SocialUserRepository;
import com.fafafashop.lyrics.repository.search.SocialUserSearchRepository;
import com.fafafashop.lyrics.service.SocialUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SocialUserResource} REST controller.
 */
@SpringBootTest(classes = LyricsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SocialUserResourceIT {

    private static final String DEFAULT_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ID_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORIZATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORIZATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN = "BBBBBBBBBB";

    @Autowired
    private SocialUserRepository socialUserRepository;

    @Autowired
    private SocialUserService socialUserService;

    /**
     * This repository is mocked in the com.fafafashop.lyrics.repository.search test package.
     *
     * @see com.fafafashop.lyrics.repository.search.SocialUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private SocialUserSearchRepository mockSocialUserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialUserMockMvc;

    private SocialUser socialUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialUser createEntity(EntityManager em) {
        SocialUser socialUser = new SocialUser()
            .provider(DEFAULT_PROVIDER)
            .socialUserId(DEFAULT_SOCIAL_USER_ID)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .photoUrl(DEFAULT_PHOTO_URL)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .authToken(DEFAULT_AUTH_TOKEN)
            .idToken(DEFAULT_ID_TOKEN)
            .authorizationCode(DEFAULT_AUTHORIZATION_CODE)
            .facebook(DEFAULT_FACEBOOK)
            .linkedIn(DEFAULT_LINKED_IN);
        return socialUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialUser createUpdatedEntity(EntityManager em) {
        SocialUser socialUser = new SocialUser()
            .provider(UPDATED_PROVIDER)
            .socialUserId(UPDATED_SOCIAL_USER_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .photoUrl(UPDATED_PHOTO_URL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .authToken(UPDATED_AUTH_TOKEN)
            .idToken(UPDATED_ID_TOKEN)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .facebook(UPDATED_FACEBOOK)
            .linkedIn(UPDATED_LINKED_IN);
        return socialUser;
    }

    @BeforeEach
    public void initTest() {
        socialUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialUser() throws Exception {
        int databaseSizeBeforeCreate = socialUserRepository.findAll().size();
        // Create the SocialUser
        restSocialUserMockMvc.perform(post("/api/social-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialUser)))
            .andExpect(status().isCreated());

        // Validate the SocialUser in the database
        List<SocialUser> socialUserList = socialUserRepository.findAll();
        assertThat(socialUserList).hasSize(databaseSizeBeforeCreate + 1);
        SocialUser testSocialUser = socialUserList.get(socialUserList.size() - 1);
        assertThat(testSocialUser.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testSocialUser.getSocialUserId()).isEqualTo(DEFAULT_SOCIAL_USER_ID);
        assertThat(testSocialUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSocialUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialUser.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testSocialUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSocialUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSocialUser.getAuthToken()).isEqualTo(DEFAULT_AUTH_TOKEN);
        assertThat(testSocialUser.getIdToken()).isEqualTo(DEFAULT_ID_TOKEN);
        assertThat(testSocialUser.getAuthorizationCode()).isEqualTo(DEFAULT_AUTHORIZATION_CODE);
        assertThat(testSocialUser.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testSocialUser.getLinkedIn()).isEqualTo(DEFAULT_LINKED_IN);

        // Validate the SocialUser in Elasticsearch
        verify(mockSocialUserSearchRepository, times(1)).save(testSocialUser);
    }

    @Test
    @Transactional
    public void createSocialUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialUserRepository.findAll().size();

        // Create the SocialUser with an existing ID
        socialUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialUserMockMvc.perform(post("/api/social-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialUser)))
            .andExpect(status().isBadRequest());

        // Validate the SocialUser in the database
        List<SocialUser> socialUserList = socialUserRepository.findAll();
        assertThat(socialUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the SocialUser in Elasticsearch
        verify(mockSocialUserSearchRepository, times(0)).save(socialUser);
    }


    @Test
    @Transactional
    public void getAllSocialUsers() throws Exception {
        // Initialize the database
        socialUserRepository.saveAndFlush(socialUser);

        // Get all the socialUserList
        restSocialUserMockMvc.perform(get("/api/social-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].socialUserId").value(hasItem(DEFAULT_SOCIAL_USER_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN)))
            .andExpect(jsonPath("$.[*].idToken").value(hasItem(DEFAULT_ID_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN.toString())));
    }
    
    @Test
    @Transactional
    public void getSocialUser() throws Exception {
        // Initialize the database
        socialUserRepository.saveAndFlush(socialUser);

        // Get the socialUser
        restSocialUserMockMvc.perform(get("/api/social-users/{id}", socialUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialUser.getId().intValue()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.socialUserId").value(DEFAULT_SOCIAL_USER_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.authToken").value(DEFAULT_AUTH_TOKEN))
            .andExpect(jsonPath("$.idToken").value(DEFAULT_ID_TOKEN.toString()))
            .andExpect(jsonPath("$.authorizationCode").value(DEFAULT_AUTHORIZATION_CODE))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.linkedIn").value(DEFAULT_LINKED_IN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSocialUser() throws Exception {
        // Get the socialUser
        restSocialUserMockMvc.perform(get("/api/social-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialUser() throws Exception {
        // Initialize the database
        socialUserService.save(socialUser);

        int databaseSizeBeforeUpdate = socialUserRepository.findAll().size();

        // Update the socialUser
        SocialUser updatedSocialUser = socialUserRepository.findById(socialUser.getId()).get();
        // Disconnect from session so that the updates on updatedSocialUser are not directly saved in db
        em.detach(updatedSocialUser);
        updatedSocialUser
            .provider(UPDATED_PROVIDER)
            .socialUserId(UPDATED_SOCIAL_USER_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .photoUrl(UPDATED_PHOTO_URL)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .authToken(UPDATED_AUTH_TOKEN)
            .idToken(UPDATED_ID_TOKEN)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .facebook(UPDATED_FACEBOOK)
            .linkedIn(UPDATED_LINKED_IN);

        restSocialUserMockMvc.perform(put("/api/social-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialUser)))
            .andExpect(status().isOk());

        // Validate the SocialUser in the database
        List<SocialUser> socialUserList = socialUserRepository.findAll();
        assertThat(socialUserList).hasSize(databaseSizeBeforeUpdate);
        SocialUser testSocialUser = socialUserList.get(socialUserList.size() - 1);
        assertThat(testSocialUser.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testSocialUser.getSocialUserId()).isEqualTo(UPDATED_SOCIAL_USER_ID);
        assertThat(testSocialUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSocialUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialUser.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testSocialUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSocialUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSocialUser.getAuthToken()).isEqualTo(UPDATED_AUTH_TOKEN);
        assertThat(testSocialUser.getIdToken()).isEqualTo(UPDATED_ID_TOKEN);
        assertThat(testSocialUser.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testSocialUser.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testSocialUser.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);

        // Validate the SocialUser in Elasticsearch
        verify(mockSocialUserSearchRepository, times(2)).save(testSocialUser);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialUser() throws Exception {
        int databaseSizeBeforeUpdate = socialUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialUserMockMvc.perform(put("/api/social-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(socialUser)))
            .andExpect(status().isBadRequest());

        // Validate the SocialUser in the database
        List<SocialUser> socialUserList = socialUserRepository.findAll();
        assertThat(socialUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SocialUser in Elasticsearch
        verify(mockSocialUserSearchRepository, times(0)).save(socialUser);
    }

    @Test
    @Transactional
    public void deleteSocialUser() throws Exception {
        // Initialize the database
        socialUserService.save(socialUser);

        int databaseSizeBeforeDelete = socialUserRepository.findAll().size();

        // Delete the socialUser
        restSocialUserMockMvc.perform(delete("/api/social-users/{id}", socialUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialUser> socialUserList = socialUserRepository.findAll();
        assertThat(socialUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SocialUser in Elasticsearch
        verify(mockSocialUserSearchRepository, times(1)).deleteById(socialUser.getId());
    }

    @Test
    @Transactional
    public void searchSocialUser() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        socialUserService.save(socialUser);
        when(mockSocialUserSearchRepository.search(queryStringQuery("id:" + socialUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(socialUser), PageRequest.of(0, 1), 1));

        // Search the socialUser
        restSocialUserMockMvc.perform(get("/api/_search/social-users?query=id:" + socialUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER)))
            .andExpect(jsonPath("$.[*].socialUserId").value(hasItem(DEFAULT_SOCIAL_USER_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN)))
            .andExpect(jsonPath("$.[*].idToken").value(hasItem(DEFAULT_ID_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN.toString())));
    }
}
