package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.domain.Lyrics;
import com.fafafashop.lyrics.service.LyricsService;
import com.fafafashop.lyrics.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.fafafashop.lyrics.domain.Lyrics}.
 */
@RestController
@RequestMapping("/api")
public class LyricsResource {

    private final Logger log = LoggerFactory.getLogger(LyricsResource.class);

    private static final String ENTITY_NAME = "lyrics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LyricsService lyricsService;

    public LyricsResource(LyricsService lyricsService) {
        this.lyricsService = lyricsService;
    }

    /**
     * {@code POST  /lyrics} : Create a new lyrics.
     *
     * @param lyrics the lyrics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lyrics, or with status {@code 400 (Bad Request)} if the lyrics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lyrics")
    public ResponseEntity<Lyrics> createLyrics(@RequestBody Lyrics lyrics) throws URISyntaxException {
        log.debug("REST request to save Lyrics : {}", lyrics);
        if (lyrics.getId() != null) {
            throw new BadRequestAlertException("A new lyrics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lyrics result = lyricsService.save(lyrics);
        return ResponseEntity.created(new URI("/api/lyrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lyrics} : Updates an existing lyrics.
     *
     * @param lyrics the lyrics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lyrics,
     * or with status {@code 400 (Bad Request)} if the lyrics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lyrics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lyrics")
    public ResponseEntity<Lyrics> updateLyrics(@RequestBody Lyrics lyrics) throws URISyntaxException {
        log.debug("REST request to update Lyrics : {}", lyrics);
        if (lyrics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lyrics result = lyricsService.save(lyrics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lyrics.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lyrics} : get all the lyrics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lyrics in body.
     */
    @GetMapping("/lyrics")
    public ResponseEntity<List<Lyrics>> getAllLyrics(Pageable pageable) {
        log.debug("REST request to get a page of Lyrics");
        Page<Lyrics> page = lyricsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /lyrics/:id} : get the "id" lyrics.
     *
     * @param id the id of the lyrics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lyrics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lyrics/{id}")
    public ResponseEntity<Lyrics> getLyrics(@PathVariable Long id) {
        log.debug("REST request to get Lyrics : {}", id);
        Optional<Lyrics> lyrics = lyricsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lyrics);
    }

    /**
     * {@code DELETE  /lyrics/:id} : delete the "id" lyrics.
     *
     * @param id the id of the lyrics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lyrics/{id}")
    public ResponseEntity<Void> deleteLyrics(@PathVariable Long id) {
        log.debug("REST request to delete Lyrics : {}", id);
        lyricsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/lyrics?query=:query} : search for the lyrics corresponding
     * to the query.
     *
     * @param query the query of the lyrics search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/lyrics")
    public ResponseEntity<List<Lyrics>> searchLyrics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lyrics for query {}", query);
        Page<Lyrics> page = lyricsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
