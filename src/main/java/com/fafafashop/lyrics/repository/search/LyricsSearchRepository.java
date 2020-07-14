package com.fafafashop.lyrics.repository.search;

import com.fafafashop.lyrics.domain.Lyrics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Lyrics} entity.
 */
public interface LyricsSearchRepository extends ElasticsearchRepository<Lyrics, Long> {
}
