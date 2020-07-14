package com.fafafashop.lyrics.service.impl;

import com.fafafashop.lyrics.service.SongService;
import com.fafafashop.lyrics.domain.Song;
import com.fafafashop.lyrics.repository.SongRepository;
import com.fafafashop.lyrics.repository.search.SongSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Song}.
 */
@Service
@Transactional
public class SongServiceImpl implements SongService {

    private final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;

    private final SongSearchRepository songSearchRepository;

    public SongServiceImpl(SongRepository songRepository, SongSearchRepository songSearchRepository) {
        this.songRepository = songRepository;
        this.songSearchRepository = songSearchRepository;
    }

    @Override
    public Song save(Song song) {
        log.debug("Request to save Song : {}", song);
        Song result = songRepository.save(song);
        songSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Song> findAll(Pageable pageable) {
        log.debug("Request to get all Songs");
        return songRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Song> findOne(Long id) {
        log.debug("Request to get Song : {}", id);
        return songRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Song : {}", id);
        songRepository.deleteById(id);
        songSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Song> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Songs for query {}", query);
        return songSearchRepository.search(queryStringQuery(query), pageable);    }
}
