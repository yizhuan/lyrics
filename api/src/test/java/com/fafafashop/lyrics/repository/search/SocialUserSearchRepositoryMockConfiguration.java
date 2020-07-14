package com.fafafashop.lyrics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SocialUserSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SocialUserSearchRepositoryMockConfiguration {

    @MockBean
    private SocialUserSearchRepository mockSocialUserSearchRepository;

}
