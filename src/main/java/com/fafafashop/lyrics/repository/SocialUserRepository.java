package com.fafafashop.lyrics.repository;

import com.fafafashop.lyrics.domain.SocialUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SocialUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
