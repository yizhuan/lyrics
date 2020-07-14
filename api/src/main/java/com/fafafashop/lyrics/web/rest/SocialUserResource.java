package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.domain.SocialUser;
import com.fafafashop.lyrics.service.SocialUserService;
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
 * REST controller for managing {@link com.fafafashop.lyrics.domain.SocialUser}.
 */
@RestController
@RequestMapping("/api")
public class SocialUserResource {

    private final Logger log = LoggerFactory.getLogger(SocialUserResource.class);

    private static final String ENTITY_NAME = "socialUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialUserService socialUserService;

    public SocialUserResource(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }

    /**
     * {@code POST  /social-users} : Create a new socialUser.
     *
     * @param socialUser the socialUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialUser, or with status {@code 400 (Bad Request)} if the socialUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-users")
    public ResponseEntity<SocialUser> createSocialUser(@RequestBody SocialUser socialUser) throws URISyntaxException {
        log.debug("REST request to save SocialUser : {}", socialUser);
        if (socialUser.getId() != null) {
            throw new BadRequestAlertException("A new socialUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialUser result = socialUserService.save(socialUser);
        return ResponseEntity.created(new URI("/api/social-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-users} : Updates an existing socialUser.
     *
     * @param socialUser the socialUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialUser,
     * or with status {@code 400 (Bad Request)} if the socialUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-users")
    public ResponseEntity<SocialUser> updateSocialUser(@RequestBody SocialUser socialUser) throws URISyntaxException {
        log.debug("REST request to update SocialUser : {}", socialUser);
        if (socialUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SocialUser result = socialUserService.save(socialUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /social-users} : get all the socialUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialUsers in body.
     */
    @GetMapping("/social-users")
    public ResponseEntity<List<SocialUser>> getAllSocialUsers(Pageable pageable) {
        log.debug("REST request to get a page of SocialUsers");
        Page<SocialUser> page = socialUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /social-users/:id} : get the "id" socialUser.
     *
     * @param id the id of the socialUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-users/{id}")
    public ResponseEntity<SocialUser> getSocialUser(@PathVariable Long id) {
        log.debug("REST request to get SocialUser : {}", id);
        Optional<SocialUser> socialUser = socialUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialUser);
    }

    /**
     * {@code DELETE  /social-users/:id} : delete the "id" socialUser.
     *
     * @param id the id of the socialUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-users/{id}")
    public ResponseEntity<Void> deleteSocialUser(@PathVariable Long id) {
        log.debug("REST request to delete SocialUser : {}", id);
        socialUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/social-users?query=:query} : search for the socialUser corresponding
     * to the query.
     *
     * @param query the query of the socialUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/social-users")
    public ResponseEntity<List<SocialUser>> searchSocialUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SocialUsers for query {}", query);
        Page<SocialUser> page = socialUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
