package com.fafafashop.lyrics.service;

import com.fafafashop.lyrics.domain.SocialUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SocialUser}.
 */
public interface SocialUserService {

    /**
     * Save a socialUser.
     *
     * @param socialUser the entity to save.
     * @return the persisted entity.
     */
    SocialUser save(SocialUser socialUser);

    /**
     * Get all the socialUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocialUser> findAll(Pageable pageable);


    /**
     * Get the "id" socialUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialUser> findOne(Long id);

    /**
     * Delete the "id" socialUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the socialUser corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocialUser> search(String query, Pageable pageable);
}
