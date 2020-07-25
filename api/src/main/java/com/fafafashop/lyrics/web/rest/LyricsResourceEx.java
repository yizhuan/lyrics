package com.fafafashop.lyrics.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fafafashop.lyrics.domain.Lyrics;
import com.fafafashop.lyrics.service.LyricsService;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fafafashop.lyrics.domain.Lyrics}.
 */
@RestController
@RequestMapping("/api")
public class LyricsResourceEx {

    private final Logger log = LoggerFactory.getLogger(LyricsResource.class);

    private static final String ENTITY_NAME = "lyrics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LyricsService lyricsService;

    public LyricsResourceEx(LyricsService lyricsService) {
        this.lyricsService = lyricsService;
    }
    
    /**
     * {@code GET  /songs{songId}/lyrics/{lang}} : find the lyrics of a language.
     *
     * @param songId song ID
     * @param lang the language code of the lyrics, e.g.: en, zh-CN
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the lyrics in body.
     */
    @GetMapping("/songs/{songId}/lyrics/{lang}")
    public ResponseEntity<Lyrics> getLyrics(@PathVariable Long songId, @PathVariable String lang, Pageable pageable) {
        log.debug("REST request to get a page of Lyrics");
        Optional<Lyrics> lyrics = lyricsService.findOneBySongIdAndLang(songId, lang);
        return ResponseUtil.wrapOrNotFound(lyrics);
    }
    
    
    /**
     * {@code GET  /lyrics} : get all the lyrics of a song.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lyrics in body.
     */
    @GetMapping("/songs/{songId}/lyrics")
    public ResponseEntity<List<Lyrics>> getAllLyrics(@PathVariable Long songId, Pageable pageable) {
        log.debug("REST request to get a page of Lyrics");
        Page<Lyrics> page = lyricsService.findAllBySongId(songId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }    
    
	
}
