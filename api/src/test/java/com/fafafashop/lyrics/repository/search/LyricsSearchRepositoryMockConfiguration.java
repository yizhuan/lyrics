package com.fafafashop.lyrics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LyricsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LyricsSearchRepositoryMockConfiguration {

    @MockBean
    private LyricsSearchRepository mockLyricsSearchRepository;

}
