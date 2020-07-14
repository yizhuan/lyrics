package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.LyricsApp;
import com.fafafashop.lyrics.domain.Song;
import com.fafafashop.lyrics.repository.SongRepository;
import com.fafafashop.lyrics.repository.search.SongSearchRepository;
import com.fafafashop.lyrics.service.SongService;

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
 * Integration tests for the {@link SongResource} REST controller.
 */
@SpringBootTest(classes = LyricsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SongResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPOSED_BY = "AAAAAAAAAA";
    private static final String UPDATED_COMPOSED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_ARTIST = "AAAAAAAAAA";
    private static final String UPDATED_ARTIST = "BBBBBBBBBB";

    private static final String DEFAULT_ALBUM = "AAAAAAAAAA";
    private static final String UPDATED_ALBUM = "BBBBBBBBBB";

    private static final String DEFAULT_BAND = "AAAAAAAAAA";
    private static final String UPDATED_BAND = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_COPYRIGHT = "AAAAAAAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIO_URL = "AAAAAAAAAA";
    private static final String UPDATED_AUDIO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ENTERED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ENTERED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    /**
     * This repository is mocked in the com.fafafashop.lyrics.repository.search test package.
     *
     * @see com.fafafashop.lyrics.repository.search.SongSearchRepositoryMockConfiguration
     */
    @Autowired
    private SongSearchRepository mockSongSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSongMockMvc;

    private Song song;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Song createEntity(EntityManager em) {
        Song song = new Song()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .composedBy(DEFAULT_COMPOSED_BY)
            .artist(DEFAULT_ARTIST)
            .album(DEFAULT_ALBUM)
            .band(DEFAULT_BAND)
            .year(DEFAULT_YEAR)
            .copyright(DEFAULT_COPYRIGHT)
            .lang(DEFAULT_LANG)
            .audioUrl(DEFAULT_AUDIO_URL)
            .videoUrl(DEFAULT_VIDEO_URL)
            .enteredBy(DEFAULT_ENTERED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return song;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Song createUpdatedEntity(EntityManager em) {
        Song song = new Song()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .composedBy(UPDATED_COMPOSED_BY)
            .artist(UPDATED_ARTIST)
            .album(UPDATED_ALBUM)
            .band(UPDATED_BAND)
            .year(UPDATED_YEAR)
            .copyright(UPDATED_COPYRIGHT)
            .lang(UPDATED_LANG)
            .audioUrl(UPDATED_AUDIO_URL)
            .videoUrl(UPDATED_VIDEO_URL)
            .enteredBy(UPDATED_ENTERED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);
        return song;
    }

    @BeforeEach
    public void initTest() {
        song = createEntity(em);
    }

    @Test
    @Transactional
    public void createSong() throws Exception {
        int databaseSizeBeforeCreate = songRepository.findAll().size();
        // Create the Song
        restSongMockMvc.perform(post("/api/songs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(song)))
            .andExpect(status().isCreated());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeCreate + 1);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSong.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSong.getComposedBy()).isEqualTo(DEFAULT_COMPOSED_BY);
        assertThat(testSong.getArtist()).isEqualTo(DEFAULT_ARTIST);
        assertThat(testSong.getAlbum()).isEqualTo(DEFAULT_ALBUM);
        assertThat(testSong.getBand()).isEqualTo(DEFAULT_BAND);
        assertThat(testSong.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSong.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testSong.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testSong.getAudioUrl()).isEqualTo(DEFAULT_AUDIO_URL);
        assertThat(testSong.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testSong.getEnteredBy()).isEqualTo(DEFAULT_ENTERED_BY);
        assertThat(testSong.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the Song in Elasticsearch
        verify(mockSongSearchRepository, times(1)).save(testSong);
    }

    @Test
    @Transactional
    public void createSongWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = songRepository.findAll().size();

        // Create the Song with an existing ID
        song.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSongMockMvc.perform(post("/api/songs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(song)))
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeCreate);

        // Validate the Song in Elasticsearch
        verify(mockSongSearchRepository, times(0)).save(song);
    }


    @Test
    @Transactional
    public void getAllSongs() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList
        restSongMockMvc.perform(get("/api/songs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(song.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].composedBy").value(hasItem(DEFAULT_COMPOSED_BY)))
            .andExpect(jsonPath("$.[*].artist").value(hasItem(DEFAULT_ARTIST)))
            .andExpect(jsonPath("$.[*].album").value(hasItem(DEFAULT_ALBUM)))
            .andExpect(jsonPath("$.[*].band").value(hasItem(DEFAULT_BAND)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].audioUrl").value(hasItem(DEFAULT_AUDIO_URL)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))));
    }
    
    @Test
    @Transactional
    public void getSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get the song
        restSongMockMvc.perform(get("/api/songs/{id}", song.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(song.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.composedBy").value(DEFAULT_COMPOSED_BY))
            .andExpect(jsonPath("$.artist").value(DEFAULT_ARTIST))
            .andExpect(jsonPath("$.album").value(DEFAULT_ALBUM))
            .andExpect(jsonPath("$.band").value(DEFAULT_BAND))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG))
            .andExpect(jsonPath("$.audioUrl").value(DEFAULT_AUDIO_URL))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.enteredBy").value(DEFAULT_ENTERED_BY))
            .andExpect(jsonPath("$.lastModified").value(sameInstant(DEFAULT_LAST_MODIFIED)));
    }
    @Test
    @Transactional
    public void getNonExistingSong() throws Exception {
        // Get the song
        restSongMockMvc.perform(get("/api/songs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSong() throws Exception {
        // Initialize the database
        songService.save(song);

        int databaseSizeBeforeUpdate = songRepository.findAll().size();

        // Update the song
        Song updatedSong = songRepository.findById(song.getId()).get();
        // Disconnect from session so that the updates on updatedSong are not directly saved in db
        em.detach(updatedSong);
        updatedSong
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .composedBy(UPDATED_COMPOSED_BY)
            .artist(UPDATED_ARTIST)
            .album(UPDATED_ALBUM)
            .band(UPDATED_BAND)
            .year(UPDATED_YEAR)
            .copyright(UPDATED_COPYRIGHT)
            .lang(UPDATED_LANG)
            .audioUrl(UPDATED_AUDIO_URL)
            .videoUrl(UPDATED_VIDEO_URL)
            .enteredBy(UPDATED_ENTERED_BY)
            .lastModified(UPDATED_LAST_MODIFIED);

        restSongMockMvc.perform(put("/api/songs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSong)))
            .andExpect(status().isOk());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSong.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSong.getComposedBy()).isEqualTo(UPDATED_COMPOSED_BY);
        assertThat(testSong.getArtist()).isEqualTo(UPDATED_ARTIST);
        assertThat(testSong.getAlbum()).isEqualTo(UPDATED_ALBUM);
        assertThat(testSong.getBand()).isEqualTo(UPDATED_BAND);
        assertThat(testSong.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSong.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testSong.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testSong.getAudioUrl()).isEqualTo(UPDATED_AUDIO_URL);
        assertThat(testSong.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testSong.getEnteredBy()).isEqualTo(UPDATED_ENTERED_BY);
        assertThat(testSong.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the Song in Elasticsearch
        verify(mockSongSearchRepository, times(2)).save(testSong);
    }

    @Test
    @Transactional
    public void updateNonExistingSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSongMockMvc.perform(put("/api/songs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(song)))
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Song in Elasticsearch
        verify(mockSongSearchRepository, times(0)).save(song);
    }

    @Test
    @Transactional
    public void deleteSong() throws Exception {
        // Initialize the database
        songService.save(song);

        int databaseSizeBeforeDelete = songRepository.findAll().size();

        // Delete the song
        restSongMockMvc.perform(delete("/api/songs/{id}", song.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Song in Elasticsearch
        verify(mockSongSearchRepository, times(1)).deleteById(song.getId());
    }

    @Test
    @Transactional
    public void searchSong() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        songService.save(song);
        when(mockSongSearchRepository.search(queryStringQuery("id:" + song.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(song), PageRequest.of(0, 1), 1));

        // Search the song
        restSongMockMvc.perform(get("/api/_search/songs?query=id:" + song.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(song.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].composedBy").value(hasItem(DEFAULT_COMPOSED_BY)))
            .andExpect(jsonPath("$.[*].artist").value(hasItem(DEFAULT_ARTIST)))
            .andExpect(jsonPath("$.[*].album").value(hasItem(DEFAULT_ALBUM)))
            .andExpect(jsonPath("$.[*].band").value(hasItem(DEFAULT_BAND)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].audioUrl").value(hasItem(DEFAULT_AUDIO_URL)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].enteredBy").value(hasItem(DEFAULT_ENTERED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))));
    }
}
