package com.fafafashop.lyrics.repository.search;

import com.fafafashop.lyrics.domain.Song;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Song} entity.
 */
public interface SongSearchRepository extends ElasticsearchRepository<Song, Long> {
}
