package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.LyricsApp;
import com.fafafashop.lyrics.domain.Song;
import com.fafafashop.lyrics.repository.SongRepository;
import com.fafafashop.lyrics.repository.search.SongSearchRepository;
import com.fafafashop.lyrics.service.SongService;
import com.fafafashop.lyrics.service.dto.SongCriteria;
import com.fafafashop.lyrics.service.SongQueryService;

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
    private static final ZonedDateTime SMALLER_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

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
    private SongQueryService songQueryService;

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
    public void getSongsByIdFiltering() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        Long id = song.getId();

        defaultSongShouldBeFound("id.equals=" + id);
        defaultSongShouldNotBeFound("id.notEquals=" + id);

        defaultSongShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSongShouldNotBeFound("id.greaterThan=" + id);

        defaultSongShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSongShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSongsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name equals to DEFAULT_NAME
        defaultSongShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the songList where name equals to UPDATED_NAME
        defaultSongShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSongsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name not equals to DEFAULT_NAME
        defaultSongShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the songList where name not equals to UPDATED_NAME
        defaultSongShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSongsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSongShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the songList where name equals to UPDATED_NAME
        defaultSongShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSongsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name is not null
        defaultSongShouldBeFound("name.specified=true");

        // Get all the songList where name is null
        defaultSongShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByNameContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name contains DEFAULT_NAME
        defaultSongShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the songList where name contains UPDATED_NAME
        defaultSongShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSongsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where name does not contain DEFAULT_NAME
        defaultSongShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the songList where name does not contain UPDATED_NAME
        defaultSongShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSongsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description equals to DEFAULT_DESCRIPTION
        defaultSongShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the songList where description equals to UPDATED_DESCRIPTION
        defaultSongShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSongsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description not equals to DEFAULT_DESCRIPTION
        defaultSongShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the songList where description not equals to UPDATED_DESCRIPTION
        defaultSongShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSongsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSongShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the songList where description equals to UPDATED_DESCRIPTION
        defaultSongShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSongsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description is not null
        defaultSongShouldBeFound("description.specified=true");

        // Get all the songList where description is null
        defaultSongShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description contains DEFAULT_DESCRIPTION
        defaultSongShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the songList where description contains UPDATED_DESCRIPTION
        defaultSongShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSongsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where description does not contain DEFAULT_DESCRIPTION
        defaultSongShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the songList where description does not contain UPDATED_DESCRIPTION
        defaultSongShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSongsByComposedByIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy equals to DEFAULT_COMPOSED_BY
        defaultSongShouldBeFound("composedBy.equals=" + DEFAULT_COMPOSED_BY);

        // Get all the songList where composedBy equals to UPDATED_COMPOSED_BY
        defaultSongShouldNotBeFound("composedBy.equals=" + UPDATED_COMPOSED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByComposedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy not equals to DEFAULT_COMPOSED_BY
        defaultSongShouldNotBeFound("composedBy.notEquals=" + DEFAULT_COMPOSED_BY);

        // Get all the songList where composedBy not equals to UPDATED_COMPOSED_BY
        defaultSongShouldBeFound("composedBy.notEquals=" + UPDATED_COMPOSED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByComposedByIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy in DEFAULT_COMPOSED_BY or UPDATED_COMPOSED_BY
        defaultSongShouldBeFound("composedBy.in=" + DEFAULT_COMPOSED_BY + "," + UPDATED_COMPOSED_BY);

        // Get all the songList where composedBy equals to UPDATED_COMPOSED_BY
        defaultSongShouldNotBeFound("composedBy.in=" + UPDATED_COMPOSED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByComposedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy is not null
        defaultSongShouldBeFound("composedBy.specified=true");

        // Get all the songList where composedBy is null
        defaultSongShouldNotBeFound("composedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByComposedByContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy contains DEFAULT_COMPOSED_BY
        defaultSongShouldBeFound("composedBy.contains=" + DEFAULT_COMPOSED_BY);

        // Get all the songList where composedBy contains UPDATED_COMPOSED_BY
        defaultSongShouldNotBeFound("composedBy.contains=" + UPDATED_COMPOSED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByComposedByNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where composedBy does not contain DEFAULT_COMPOSED_BY
        defaultSongShouldNotBeFound("composedBy.doesNotContain=" + DEFAULT_COMPOSED_BY);

        // Get all the songList where composedBy does not contain UPDATED_COMPOSED_BY
        defaultSongShouldBeFound("composedBy.doesNotContain=" + UPDATED_COMPOSED_BY);
    }


    @Test
    @Transactional
    public void getAllSongsByArtistIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist equals to DEFAULT_ARTIST
        defaultSongShouldBeFound("artist.equals=" + DEFAULT_ARTIST);

        // Get all the songList where artist equals to UPDATED_ARTIST
        defaultSongShouldNotBeFound("artist.equals=" + UPDATED_ARTIST);
    }

    @Test
    @Transactional
    public void getAllSongsByArtistIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist not equals to DEFAULT_ARTIST
        defaultSongShouldNotBeFound("artist.notEquals=" + DEFAULT_ARTIST);

        // Get all the songList where artist not equals to UPDATED_ARTIST
        defaultSongShouldBeFound("artist.notEquals=" + UPDATED_ARTIST);
    }

    @Test
    @Transactional
    public void getAllSongsByArtistIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist in DEFAULT_ARTIST or UPDATED_ARTIST
        defaultSongShouldBeFound("artist.in=" + DEFAULT_ARTIST + "," + UPDATED_ARTIST);

        // Get all the songList where artist equals to UPDATED_ARTIST
        defaultSongShouldNotBeFound("artist.in=" + UPDATED_ARTIST);
    }

    @Test
    @Transactional
    public void getAllSongsByArtistIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist is not null
        defaultSongShouldBeFound("artist.specified=true");

        // Get all the songList where artist is null
        defaultSongShouldNotBeFound("artist.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByArtistContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist contains DEFAULT_ARTIST
        defaultSongShouldBeFound("artist.contains=" + DEFAULT_ARTIST);

        // Get all the songList where artist contains UPDATED_ARTIST
        defaultSongShouldNotBeFound("artist.contains=" + UPDATED_ARTIST);
    }

    @Test
    @Transactional
    public void getAllSongsByArtistNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where artist does not contain DEFAULT_ARTIST
        defaultSongShouldNotBeFound("artist.doesNotContain=" + DEFAULT_ARTIST);

        // Get all the songList where artist does not contain UPDATED_ARTIST
        defaultSongShouldBeFound("artist.doesNotContain=" + UPDATED_ARTIST);
    }


    @Test
    @Transactional
    public void getAllSongsByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album equals to DEFAULT_ALBUM
        defaultSongShouldBeFound("album.equals=" + DEFAULT_ALBUM);

        // Get all the songList where album equals to UPDATED_ALBUM
        defaultSongShouldNotBeFound("album.equals=" + UPDATED_ALBUM);
    }

    @Test
    @Transactional
    public void getAllSongsByAlbumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album not equals to DEFAULT_ALBUM
        defaultSongShouldNotBeFound("album.notEquals=" + DEFAULT_ALBUM);

        // Get all the songList where album not equals to UPDATED_ALBUM
        defaultSongShouldBeFound("album.notEquals=" + UPDATED_ALBUM);
    }

    @Test
    @Transactional
    public void getAllSongsByAlbumIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album in DEFAULT_ALBUM or UPDATED_ALBUM
        defaultSongShouldBeFound("album.in=" + DEFAULT_ALBUM + "," + UPDATED_ALBUM);

        // Get all the songList where album equals to UPDATED_ALBUM
        defaultSongShouldNotBeFound("album.in=" + UPDATED_ALBUM);
    }

    @Test
    @Transactional
    public void getAllSongsByAlbumIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album is not null
        defaultSongShouldBeFound("album.specified=true");

        // Get all the songList where album is null
        defaultSongShouldNotBeFound("album.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByAlbumContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album contains DEFAULT_ALBUM
        defaultSongShouldBeFound("album.contains=" + DEFAULT_ALBUM);

        // Get all the songList where album contains UPDATED_ALBUM
        defaultSongShouldNotBeFound("album.contains=" + UPDATED_ALBUM);
    }

    @Test
    @Transactional
    public void getAllSongsByAlbumNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where album does not contain DEFAULT_ALBUM
        defaultSongShouldNotBeFound("album.doesNotContain=" + DEFAULT_ALBUM);

        // Get all the songList where album does not contain UPDATED_ALBUM
        defaultSongShouldBeFound("album.doesNotContain=" + UPDATED_ALBUM);
    }


    @Test
    @Transactional
    public void getAllSongsByBandIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band equals to DEFAULT_BAND
        defaultSongShouldBeFound("band.equals=" + DEFAULT_BAND);

        // Get all the songList where band equals to UPDATED_BAND
        defaultSongShouldNotBeFound("band.equals=" + UPDATED_BAND);
    }

    @Test
    @Transactional
    public void getAllSongsByBandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band not equals to DEFAULT_BAND
        defaultSongShouldNotBeFound("band.notEquals=" + DEFAULT_BAND);

        // Get all the songList where band not equals to UPDATED_BAND
        defaultSongShouldBeFound("band.notEquals=" + UPDATED_BAND);
    }

    @Test
    @Transactional
    public void getAllSongsByBandIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band in DEFAULT_BAND or UPDATED_BAND
        defaultSongShouldBeFound("band.in=" + DEFAULT_BAND + "," + UPDATED_BAND);

        // Get all the songList where band equals to UPDATED_BAND
        defaultSongShouldNotBeFound("band.in=" + UPDATED_BAND);
    }

    @Test
    @Transactional
    public void getAllSongsByBandIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band is not null
        defaultSongShouldBeFound("band.specified=true");

        // Get all the songList where band is null
        defaultSongShouldNotBeFound("band.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByBandContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band contains DEFAULT_BAND
        defaultSongShouldBeFound("band.contains=" + DEFAULT_BAND);

        // Get all the songList where band contains UPDATED_BAND
        defaultSongShouldNotBeFound("band.contains=" + UPDATED_BAND);
    }

    @Test
    @Transactional
    public void getAllSongsByBandNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where band does not contain DEFAULT_BAND
        defaultSongShouldNotBeFound("band.doesNotContain=" + DEFAULT_BAND);

        // Get all the songList where band does not contain UPDATED_BAND
        defaultSongShouldBeFound("band.doesNotContain=" + UPDATED_BAND);
    }


    @Test
    @Transactional
    public void getAllSongsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year equals to DEFAULT_YEAR
        defaultSongShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the songList where year equals to UPDATED_YEAR
        defaultSongShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSongsByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year not equals to DEFAULT_YEAR
        defaultSongShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the songList where year not equals to UPDATED_YEAR
        defaultSongShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSongsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSongShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the songList where year equals to UPDATED_YEAR
        defaultSongShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSongsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year is not null
        defaultSongShouldBeFound("year.specified=true");

        // Get all the songList where year is null
        defaultSongShouldNotBeFound("year.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByYearContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year contains DEFAULT_YEAR
        defaultSongShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the songList where year contains UPDATED_YEAR
        defaultSongShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSongsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where year does not contain DEFAULT_YEAR
        defaultSongShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the songList where year does not contain UPDATED_YEAR
        defaultSongShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }


    @Test
    @Transactional
    public void getAllSongsByCopyrightIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright equals to DEFAULT_COPYRIGHT
        defaultSongShouldBeFound("copyright.equals=" + DEFAULT_COPYRIGHT);

        // Get all the songList where copyright equals to UPDATED_COPYRIGHT
        defaultSongShouldNotBeFound("copyright.equals=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    public void getAllSongsByCopyrightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright not equals to DEFAULT_COPYRIGHT
        defaultSongShouldNotBeFound("copyright.notEquals=" + DEFAULT_COPYRIGHT);

        // Get all the songList where copyright not equals to UPDATED_COPYRIGHT
        defaultSongShouldBeFound("copyright.notEquals=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    public void getAllSongsByCopyrightIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright in DEFAULT_COPYRIGHT or UPDATED_COPYRIGHT
        defaultSongShouldBeFound("copyright.in=" + DEFAULT_COPYRIGHT + "," + UPDATED_COPYRIGHT);

        // Get all the songList where copyright equals to UPDATED_COPYRIGHT
        defaultSongShouldNotBeFound("copyright.in=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    public void getAllSongsByCopyrightIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright is not null
        defaultSongShouldBeFound("copyright.specified=true");

        // Get all the songList where copyright is null
        defaultSongShouldNotBeFound("copyright.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByCopyrightContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright contains DEFAULT_COPYRIGHT
        defaultSongShouldBeFound("copyright.contains=" + DEFAULT_COPYRIGHT);

        // Get all the songList where copyright contains UPDATED_COPYRIGHT
        defaultSongShouldNotBeFound("copyright.contains=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    public void getAllSongsByCopyrightNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where copyright does not contain DEFAULT_COPYRIGHT
        defaultSongShouldNotBeFound("copyright.doesNotContain=" + DEFAULT_COPYRIGHT);

        // Get all the songList where copyright does not contain UPDATED_COPYRIGHT
        defaultSongShouldBeFound("copyright.doesNotContain=" + UPDATED_COPYRIGHT);
    }


    @Test
    @Transactional
    public void getAllSongsByLangIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang equals to DEFAULT_LANG
        defaultSongShouldBeFound("lang.equals=" + DEFAULT_LANG);

        // Get all the songList where lang equals to UPDATED_LANG
        defaultSongShouldNotBeFound("lang.equals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllSongsByLangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang not equals to DEFAULT_LANG
        defaultSongShouldNotBeFound("lang.notEquals=" + DEFAULT_LANG);

        // Get all the songList where lang not equals to UPDATED_LANG
        defaultSongShouldBeFound("lang.notEquals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllSongsByLangIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang in DEFAULT_LANG or UPDATED_LANG
        defaultSongShouldBeFound("lang.in=" + DEFAULT_LANG + "," + UPDATED_LANG);

        // Get all the songList where lang equals to UPDATED_LANG
        defaultSongShouldNotBeFound("lang.in=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllSongsByLangIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang is not null
        defaultSongShouldBeFound("lang.specified=true");

        // Get all the songList where lang is null
        defaultSongShouldNotBeFound("lang.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByLangContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang contains DEFAULT_LANG
        defaultSongShouldBeFound("lang.contains=" + DEFAULT_LANG);

        // Get all the songList where lang contains UPDATED_LANG
        defaultSongShouldNotBeFound("lang.contains=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllSongsByLangNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lang does not contain DEFAULT_LANG
        defaultSongShouldNotBeFound("lang.doesNotContain=" + DEFAULT_LANG);

        // Get all the songList where lang does not contain UPDATED_LANG
        defaultSongShouldBeFound("lang.doesNotContain=" + UPDATED_LANG);
    }


    @Test
    @Transactional
    public void getAllSongsByAudioUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl equals to DEFAULT_AUDIO_URL
        defaultSongShouldBeFound("audioUrl.equals=" + DEFAULT_AUDIO_URL);

        // Get all the songList where audioUrl equals to UPDATED_AUDIO_URL
        defaultSongShouldNotBeFound("audioUrl.equals=" + UPDATED_AUDIO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByAudioUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl not equals to DEFAULT_AUDIO_URL
        defaultSongShouldNotBeFound("audioUrl.notEquals=" + DEFAULT_AUDIO_URL);

        // Get all the songList where audioUrl not equals to UPDATED_AUDIO_URL
        defaultSongShouldBeFound("audioUrl.notEquals=" + UPDATED_AUDIO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByAudioUrlIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl in DEFAULT_AUDIO_URL or UPDATED_AUDIO_URL
        defaultSongShouldBeFound("audioUrl.in=" + DEFAULT_AUDIO_URL + "," + UPDATED_AUDIO_URL);

        // Get all the songList where audioUrl equals to UPDATED_AUDIO_URL
        defaultSongShouldNotBeFound("audioUrl.in=" + UPDATED_AUDIO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByAudioUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl is not null
        defaultSongShouldBeFound("audioUrl.specified=true");

        // Get all the songList where audioUrl is null
        defaultSongShouldNotBeFound("audioUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByAudioUrlContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl contains DEFAULT_AUDIO_URL
        defaultSongShouldBeFound("audioUrl.contains=" + DEFAULT_AUDIO_URL);

        // Get all the songList where audioUrl contains UPDATED_AUDIO_URL
        defaultSongShouldNotBeFound("audioUrl.contains=" + UPDATED_AUDIO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByAudioUrlNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where audioUrl does not contain DEFAULT_AUDIO_URL
        defaultSongShouldNotBeFound("audioUrl.doesNotContain=" + DEFAULT_AUDIO_URL);

        // Get all the songList where audioUrl does not contain UPDATED_AUDIO_URL
        defaultSongShouldBeFound("audioUrl.doesNotContain=" + UPDATED_AUDIO_URL);
    }


    @Test
    @Transactional
    public void getAllSongsByVideoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl equals to DEFAULT_VIDEO_URL
        defaultSongShouldBeFound("videoUrl.equals=" + DEFAULT_VIDEO_URL);

        // Get all the songList where videoUrl equals to UPDATED_VIDEO_URL
        defaultSongShouldNotBeFound("videoUrl.equals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByVideoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl not equals to DEFAULT_VIDEO_URL
        defaultSongShouldNotBeFound("videoUrl.notEquals=" + DEFAULT_VIDEO_URL);

        // Get all the songList where videoUrl not equals to UPDATED_VIDEO_URL
        defaultSongShouldBeFound("videoUrl.notEquals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByVideoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl in DEFAULT_VIDEO_URL or UPDATED_VIDEO_URL
        defaultSongShouldBeFound("videoUrl.in=" + DEFAULT_VIDEO_URL + "," + UPDATED_VIDEO_URL);

        // Get all the songList where videoUrl equals to UPDATED_VIDEO_URL
        defaultSongShouldNotBeFound("videoUrl.in=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByVideoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl is not null
        defaultSongShouldBeFound("videoUrl.specified=true");

        // Get all the songList where videoUrl is null
        defaultSongShouldNotBeFound("videoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByVideoUrlContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl contains DEFAULT_VIDEO_URL
        defaultSongShouldBeFound("videoUrl.contains=" + DEFAULT_VIDEO_URL);

        // Get all the songList where videoUrl contains UPDATED_VIDEO_URL
        defaultSongShouldNotBeFound("videoUrl.contains=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllSongsByVideoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where videoUrl does not contain DEFAULT_VIDEO_URL
        defaultSongShouldNotBeFound("videoUrl.doesNotContain=" + DEFAULT_VIDEO_URL);

        // Get all the songList where videoUrl does not contain UPDATED_VIDEO_URL
        defaultSongShouldBeFound("videoUrl.doesNotContain=" + UPDATED_VIDEO_URL);
    }


    @Test
    @Transactional
    public void getAllSongsByEnteredByIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy equals to DEFAULT_ENTERED_BY
        defaultSongShouldBeFound("enteredBy.equals=" + DEFAULT_ENTERED_BY);

        // Get all the songList where enteredBy equals to UPDATED_ENTERED_BY
        defaultSongShouldNotBeFound("enteredBy.equals=" + UPDATED_ENTERED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByEnteredByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy not equals to DEFAULT_ENTERED_BY
        defaultSongShouldNotBeFound("enteredBy.notEquals=" + DEFAULT_ENTERED_BY);

        // Get all the songList where enteredBy not equals to UPDATED_ENTERED_BY
        defaultSongShouldBeFound("enteredBy.notEquals=" + UPDATED_ENTERED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByEnteredByIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy in DEFAULT_ENTERED_BY or UPDATED_ENTERED_BY
        defaultSongShouldBeFound("enteredBy.in=" + DEFAULT_ENTERED_BY + "," + UPDATED_ENTERED_BY);

        // Get all the songList where enteredBy equals to UPDATED_ENTERED_BY
        defaultSongShouldNotBeFound("enteredBy.in=" + UPDATED_ENTERED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByEnteredByIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy is not null
        defaultSongShouldBeFound("enteredBy.specified=true");

        // Get all the songList where enteredBy is null
        defaultSongShouldNotBeFound("enteredBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSongsByEnteredByContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy contains DEFAULT_ENTERED_BY
        defaultSongShouldBeFound("enteredBy.contains=" + DEFAULT_ENTERED_BY);

        // Get all the songList where enteredBy contains UPDATED_ENTERED_BY
        defaultSongShouldNotBeFound("enteredBy.contains=" + UPDATED_ENTERED_BY);
    }

    @Test
    @Transactional
    public void getAllSongsByEnteredByNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where enteredBy does not contain DEFAULT_ENTERED_BY
        defaultSongShouldNotBeFound("enteredBy.doesNotContain=" + DEFAULT_ENTERED_BY);

        // Get all the songList where enteredBy does not contain UPDATED_ENTERED_BY
        defaultSongShouldBeFound("enteredBy.doesNotContain=" + UPDATED_ENTERED_BY);
    }


    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the songList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified is not null
        defaultSongShouldBeFound("lastModified.specified=true");

        // Get all the songList where lastModified is null
        defaultSongShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified is greater than or equal to DEFAULT_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified is greater than or equal to UPDATED_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified is less than or equal to DEFAULT_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified is less than or equal to SMALLER_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.lessThanOrEqual=" + SMALLER_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified is less than DEFAULT_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.lessThan=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified is less than UPDATED_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.lessThan=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllSongsByLastModifiedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where lastModified is greater than DEFAULT_LAST_MODIFIED
        defaultSongShouldNotBeFound("lastModified.greaterThan=" + DEFAULT_LAST_MODIFIED);

        // Get all the songList where lastModified is greater than SMALLER_LAST_MODIFIED
        defaultSongShouldBeFound("lastModified.greaterThan=" + SMALLER_LAST_MODIFIED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSongShouldBeFound(String filter) throws Exception {
        restSongMockMvc.perform(get("/api/songs?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restSongMockMvc.perform(get("/api/songs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSongShouldNotBeFound(String filter) throws Exception {
        restSongMockMvc.perform(get("/api/songs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSongMockMvc.perform(get("/api/songs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
