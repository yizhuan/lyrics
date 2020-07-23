package com.fafafashop.lyrics.service;

import com.fafafashop.lyrics.domain.Lyrics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Lyrics}.
 */
public interface LyricsService {

    /**
     * Save a lyrics.
     *
     * @param lyrics the entity to save.
     * @return the persisted entity.
     */
    Lyrics save(Lyrics lyrics);

    /**
     * Get all the lyrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Lyrics> findAll(Pageable pageable);


    /**
     * Get the "id" lyrics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Lyrics> findOne(Long id);

    /**
     * Delete the "id" lyrics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lyrics corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Lyrics> search(String query, Pageable pageable);
    
    
    
    Optional<Lyrics> findOneBySongIdAndLang(Long songId, String lang);
	
	Page<Lyrics> findAllBySongId(Long songId, Pageable pageable);
}
