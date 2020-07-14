package com.fafafashop.lyrics.service;

import com.fafafashop.lyrics.domain.Song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Song}.
 */
public interface SongService {

    /**
     * Save a song.
     *
     * @param song the entity to save.
     * @return the persisted entity.
     */
    Song save(Song song);

    /**
     * Get all the songs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Song> findAll(Pageable pageable);


    /**
     * Get the "id" song.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Song> findOne(Long id);

    /**
     * Delete the "id" song.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the song corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Song> search(String query, Pageable pageable);
}
