package com.fafafashop.lyrics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fafafashop.lyrics.web.rest.TestUtil;

public class LyricsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lyrics.class);
        Lyrics lyrics1 = new Lyrics();
        lyrics1.setId(1L);
        Lyrics lyrics2 = new Lyrics();
        lyrics2.setId(lyrics1.getId());
        assertThat(lyrics1).isEqualTo(lyrics2);
        lyrics2.setId(2L);
        assertThat(lyrics1).isNotEqualTo(lyrics2);
        lyrics1.setId(null);
        assertThat(lyrics1).isNotEqualTo(lyrics2);
    }
}
