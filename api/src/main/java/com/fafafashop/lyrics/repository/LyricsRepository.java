package com.fafafashop.lyrics.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fafafashop.lyrics.domain.Lyrics;

/**
 * Spring Data repository for the Lyrics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LyricsRepository extends JpaRepository<Lyrics, Long> {

	Optional<Lyrics> findOneBySongIdAndLang(Long songId, String lang);
	
	Page<Lyrics> findAllBySongId(Long songId, Pageable pageable);

}
