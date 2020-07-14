package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.LyricsApp;
import com.fafafashop.lyrics.domain.Lyrics;
import com.fafafashop.lyrics.repository.LyricsRepository;
import com.fafafashop.lyrics.repository.search.LyricsSearchRepository;
import com.fafafashop.lyrics.service.LyricsService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.fafafashop.lyrics.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LyricsResource} REST controller.
 */
@SpringBootTest(classes = LyricsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LyricsResourceIT {

    private static final Long DEFAULT_SONG_ID = 1L;
    private static final Long UPDATED_SONG_ID = 2L;

    private static final String DEFAULT_LYRICS = "AAAAAAAAAA";
    private static final String UPDATED_LYRICS = "BBBBBBBBBB";

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_COPYRIGHT = "AAAAAAAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TRANSLATED = false;
    private static final Boolean UPDATED_IS_TRANSLATED = true;

    private static final String DEFAULT_TRANSLATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CHARSET = "AAAAAAAAAA";
    private static final String UPDATED_CHARSET = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LyricsRepository lyricsRepository;

    @Autowired
    private LyricsService lyricsService;

    /**
     * This repository is mocked in the com.fafafashop.lyrics.repository.search test package.
     *
     * @see com.fafafashop.lyrics.repository.search.LyricsSearchRepositoryMockConfiguration
     */
    @Autowired
    private LyricsSearchRepository mockLyricsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLyricsMockMvc;

    private Lyrics lyrics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lyrics createEntity(EntityManager em) {
        Lyrics lyrics = new Lyrics()
            .songId(DEFAULT_SONG_ID)
            .lyrics(DEFAULT_LYRICS)
            .lang(DEFAULT_LANG)
            .author(DEFAULT_AUTHOR)
            .copyright(DEFAULT_COPYRIGHT)
            .isTranslated(DEFAULT_IS_TRANSLATED)
            .translatedBy(DEFAULT_TRANSLATED_BY)
            .charset(DEFAULT_CHARSET)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return lyrics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lyrics createUpdatedEntity(EntityManager em) {
        Lyrics lyrics = new Lyrics()
            .songId(UPDATED_SONG_ID)
            .lyrics(UPDATED_LYRICS)
            .lang(UPDATED_LANG)
            .author(UPDATED_AUTHOR)
            .copyright(UPDATED_COPYRIGHT)
            .isTranslated(UPDATED_IS_TRANSLATED)
            .translatedBy(UPDATED_TRANSLATED_BY)
            .charset(UPDATED_CHARSET)
            .lastModified(UPDATED_LAST_MODIFIED);
        return lyrics;
    }

    @BeforeEach
    public void initTest() {
        lyrics = createEntity(em);
    }

    @Test
    @Transactional
    public void createLyrics() throws Exception {
        int databaseSizeBeforeCreate = lyricsRepository.findAll().size();
        // Create the Lyrics
        restLyricsMockMvc.perform(post("/api/lyrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lyrics)))
            .andExpect(status().isCreated());

        // Validate the Lyrics in the database
        List<Lyrics> lyricsList = lyricsRepository.findAll();
        assertThat(lyricsList).hasSize(databaseSizeBeforeCreate + 1);
        Lyrics testLyrics = lyricsList.get(lyricsList.size() - 1);
        assertThat(testLyrics.getSongId()).isEqualTo(DEFAULT_SONG_ID);
        assertThat(testLyrics.getLyrics()).isEqualTo(DEFAULT_LYRICS);
        assertThat(testLyrics.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testLyrics.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testLyrics.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testLyrics.isIsTranslated()).isEqualTo(DEFAULT_IS_TRANSLATED);
        assertThat(testLyrics.getTranslatedBy()).isEqualTo(DEFAULT_TRANSLATED_BY);
        assertThat(testLyrics.getCharset()).isEqualTo(DEFAULT_CHARSET);
        assertThat(testLyrics.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the Lyrics in Elasticsearch
        verify(mockLyricsSearchRepository, times(1)).save(testLyrics);
    }

    @Test
    @Transactional
    public void createLyricsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lyricsRepository.findAll().size();

        // Create the Lyrics with an existing ID
        lyrics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLyricsMockMvc.perform(post("/api/lyrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lyrics)))
            .andExpect(status().isBadRequest());

        // Validate the Lyrics in the database
        List<Lyrics> lyricsList = lyricsRepository.findAll();
        assertThat(lyricsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lyrics in Elasticsearch
        verify(mockLyricsSearchRepository, times(0)).save(lyrics);
    }


    @Test
    @Transactional
    public void getAllLyrics() throws Exception {
        // Initialize the database
        lyricsRepository.saveAndFlush(lyrics);

        // Get all the lyricsList
        restLyricsMockMvc.perform(get("/api/lyrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lyrics.getId().intValue())))
            .andExpect(jsonPath("$.[*].songId").value(hasItem(DEFAULT_SONG_ID.intValue())))
            .andExpect(jsonPath("$.[*].lyrics").value(hasItem(DEFAULT_LYRICS.toString())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].isTranslated").value(hasItem(DEFAULT_IS_TRANSLATED.booleanValue())))
            .andExpect(jsonPath("$.[*].translatedBy").value(hasItem(DEFAULT_TRANSLATED_BY)))
            .andExpect(jsonPath("$.[*].charset").value(hasItem(DEFAULT_CHARSET)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))));
    }
    
    @Test
    @Transactional
    public void getLyrics() throws Exception {
        // Initialize the database
        lyricsRepository.saveAndFlush(lyrics);

        // Get the lyrics
        restLyricsMockMvc.perform(get("/api/lyrics/{id}", lyrics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lyrics.getId().intValue()))
            .andExpect(jsonPath("$.songId").value(DEFAULT_SONG_ID.intValue()))
            .andExpect(jsonPath("$.lyrics").value(DEFAULT_LYRICS.toString()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT))
            .andExpect(jsonPath("$.isTranslated").value(DEFAULT_IS_TRANSLATED.booleanValue()))
            .andExpect(jsonPath("$.translatedBy").value(DEFAULT_TRANSLATED_BY))
            .andExpect(jsonPath("$.charset").value(DEFAULT_CHARSET))
            .andExpect(jsonPath("$.lastModified").value(sameInstant(DEFAULT_LAST_MODIFIED)));
    }
    @Test
    @Transactional
    public void getNonExistingLyrics() throws Exception {
        // Get the lyrics
        restLyricsMockMvc.perform(get("/api/lyrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLyrics() throws Exception {
        // Initialize the database
        lyricsService.save(lyrics);

        int databaseSizeBeforeUpdate = lyricsRepository.findAll().size();

        // Update the lyrics
        Lyrics updatedLyrics = lyricsRepository.findById(lyrics.getId()).get();
        // Disconnect from session so that the updates on updatedLyrics are not directly saved in db
        em.detach(updatedLyrics);
        updatedLyrics
            .songId(UPDATED_SONG_ID)
            .lyrics(UPDATED_LYRICS)
            .lang(UPDATED_LANG)
            .author(UPDATED_AUTHOR)
            .copyright(UPDATED_COPYRIGHT)
            .isTranslated(UPDATED_IS_TRANSLATED)
            .translatedBy(UPDATED_TRANSLATED_BY)
            .charset(UPDATED_CHARSET)
            .lastModified(UPDATED_LAST_MODIFIED);

        restLyricsMockMvc.perform(put("/api/lyrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLyrics)))
            .andExpect(status().isOk());

        // Validate the Lyrics in the database
        List<Lyrics> lyricsList = lyricsRepository.findAll();
        assertThat(lyricsList).hasSize(databaseSizeBeforeUpdate);
        Lyrics testLyrics = lyricsList.get(lyricsList.size() - 1);
        assertThat(testLyrics.getSongId()).isEqualTo(UPDATED_SONG_ID);
        assertThat(testLyrics.getLyrics()).isEqualTo(UPDATED_LYRICS);
        assertThat(testLyrics.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testLyrics.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testLyrics.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testLyrics.isIsTranslated()).isEqualTo(UPDATED_IS_TRANSLATED);
        assertThat(testLyrics.getTranslatedBy()).isEqualTo(UPDATED_TRANSLATED_BY);
        assertThat(testLyrics.getCharset()).isEqualTo(UPDATED_CHARSET);
        assertThat(testLyrics.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the Lyrics in Elasticsearch
        verify(mockLyricsSearchRepository, times(2)).save(testLyrics);
    }

    @Test
    @Transactional
    public void updateNonExistingLyrics() throws Exception {
        int databaseSizeBeforeUpdate = lyricsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLyricsMockMvc.perform(put("/api/lyrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lyrics)))
            .andExpect(status().isBadRequest());

        // Validate the Lyrics in the database
        List<Lyrics> lyricsList = lyricsRepository.findAll();
        assertThat(lyricsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lyrics in Elasticsearch
        verify(mockLyricsSearchRepository, times(0)).save(lyrics);
    }

    @Test
    @Transactional
    public void deleteLyrics() throws Exception {
        // Initialize the database
        lyricsService.save(lyrics);

        int databaseSizeBeforeDelete = lyricsRepository.findAll().size();

        // Delete the lyrics
        restLyricsMockMvc.perform(delete("/api/lyrics/{id}", lyrics.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lyrics> lyricsList = lyricsRepository.findAll();
        assertThat(lyricsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lyrics in Elasticsearch
        verify(mockLyricsSearchRepository, times(1)).deleteById(lyrics.getId());
    }

    @Test
    @Transactional
    public void searchLyrics() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        lyricsService.save(lyrics);
        when(mockLyricsSearchRepository.search(queryStringQuery("id:" + lyrics.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lyrics), PageRequest.of(0, 1), 1));

        // Search the lyrics
        restLyricsMockMvc.perform(get("/api/_search/lyrics?query=id:" + lyrics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lyrics.getId().intValue())))
            .andExpect(jsonPath("$.[*].songId").value(hasItem(DEFAULT_SONG_ID.intValue())))
            .andExpect(jsonPath("$.[*].lyrics").value(hasItem(DEFAULT_LYRICS.toString())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].isTranslated").value(hasItem(DEFAULT_IS_TRANSLATED.booleanValue())))
            .andExpect(jsonPath("$.[*].translatedBy").value(hasItem(DEFAULT_TRANSLATED_BY)))
            .andExpect(jsonPath("$.[*].charset").value(hasItem(DEFAULT_CHARSET)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))));
    }
}
