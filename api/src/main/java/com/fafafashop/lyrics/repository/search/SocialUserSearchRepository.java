package com.fafafashop.lyrics.repository.search;

import com.fafafashop.lyrics.domain.SocialUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SocialUser} entity.
 */
public interface SocialUserSearchRepository extends ElasticsearchRepository<SocialUser, Long> {
}
