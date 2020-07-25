package com.fafafashop.lyrics.service.impl;

import com.fafafashop.lyrics.service.LyricsService;
import com.fafafashop.lyrics.domain.Lyrics;
import com.fafafashop.lyrics.repository.LyricsRepository;
import com.fafafashop.lyrics.repository.search.LyricsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Lyrics}.
 */
@Service
@Transactional
public class LyricsServiceImpl implements LyricsService {

    private final Logger log = LoggerFactory.getLogger(LyricsServiceImpl.class);

    private final LyricsRepository lyricsRepository;

    private final LyricsSearchRepository lyricsSearchRepository;

    public LyricsServiceImpl(LyricsRepository lyricsRepository, LyricsSearchRepository lyricsSearchRepository) {
        this.lyricsRepository = lyricsRepository;
        this.lyricsSearchRepository = lyricsSearchRepository;
    }

    @Override
    public Lyrics save(Lyrics lyrics) {
        log.debug("Request to save Lyrics : {}", lyrics);
        Lyrics result = lyricsRepository.save(lyrics);
        lyricsSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Lyrics> findAll(Pageable pageable) {
        log.debug("Request to get all Lyrics");
        return lyricsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Lyrics> findOne(Long id) {
        log.debug("Request to get Lyrics : {}", id);
        return lyricsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lyrics : {}", id);
        lyricsRepository.deleteById(id);
        lyricsSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Lyrics> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lyrics for query {}", query);
        return lyricsSearchRepository.search(queryStringQuery(query), pageable);    }
    
    
    
    public Optional<Lyrics> findOneBySongIdAndLang(Long songId, String lang) {
		return this.lyricsRepository.findOneBySongIdAndLang(songId, lang);
	}
	
    public Page<Lyrics> findAllBySongId(Long songId, Pageable pageable) {
		return this.lyricsRepository.findAllBySongId(songId, pageable);
	}
}
