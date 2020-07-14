package com.fafafashop.lyrics.repository;

import com.fafafashop.lyrics.domain.Lyrics;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lyrics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LyricsRepository extends JpaRepository<Lyrics, Long> {
}
