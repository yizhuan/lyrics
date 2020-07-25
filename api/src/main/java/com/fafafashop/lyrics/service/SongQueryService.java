package com.fafafashop.lyrics.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fafafashop.lyrics.domain.Song;
import com.fafafashop.lyrics.domain.*; // for static metamodels
import com.fafafashop.lyrics.repository.SongRepository;
import com.fafafashop.lyrics.repository.search.SongSearchRepository;
import com.fafafashop.lyrics.service.dto.SongCriteria;

/**
 * Service for executing complex queries for {@link Song} entities in the database.
 * The main input is a {@link SongCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Song} or a {@link Page} of {@link Song} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SongQueryService extends QueryService<Song> {

    private final Logger log = LoggerFactory.getLogger(SongQueryService.class);

    private final SongRepository songRepository;

    private final SongSearchRepository songSearchRepository;

    public SongQueryService(SongRepository songRepository, SongSearchRepository songSearchRepository) {
        this.songRepository = songRepository;
        this.songSearchRepository = songSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Song} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Song> findByCriteria(SongCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Song> specification = createSpecification(criteria);
        return songRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Song} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Song> findByCriteria(SongCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Song> specification = createSpecification(criteria);
        return songRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SongCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Song> specification = createSpecification(criteria);
        return songRepository.count(specification);
    }

    /**
     * Function to convert {@link SongCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Song> createSpecification(SongCriteria criteria) {
        Specification<Song> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Song_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Song_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Song_.description));
            }
            if (criteria.getComposedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComposedBy(), Song_.composedBy));
            }
            if (criteria.getArtist() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArtist(), Song_.artist));
            }
            if (criteria.getAlbum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlbum(), Song_.album));
            }
            if (criteria.getBand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBand(), Song_.band));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), Song_.year));
            }
            if (criteria.getCopyright() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCopyright(), Song_.copyright));
            }
            if (criteria.getLang() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLang(), Song_.lang));
            }
            if (criteria.getAudioUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAudioUrl(), Song_.audioUrl));
            }
            if (criteria.getVideoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideoUrl(), Song_.videoUrl));
            }
            if (criteria.getEnteredBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnteredBy(), Song_.enteredBy));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Song_.lastModified));
            }
        }
        return specification;
    }
}
