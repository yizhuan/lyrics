package com.fafafashop.lyrics.service.impl;

import com.fafafashop.lyrics.service.SocialUserService;
import com.fafafashop.lyrics.domain.SocialUser;
import com.fafafashop.lyrics.repository.SocialUserRepository;
import com.fafafashop.lyrics.repository.search.SocialUserSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SocialUser}.
 */
@Service
@Transactional
public class SocialUserServiceImpl implements SocialUserService {

    private final Logger log = LoggerFactory.getLogger(SocialUserServiceImpl.class);

    private final SocialUserRepository socialUserRepository;

    private final SocialUserSearchRepository socialUserSearchRepository;

    public SocialUserServiceImpl(SocialUserRepository socialUserRepository, SocialUserSearchRepository socialUserSearchRepository) {
        this.socialUserRepository = socialUserRepository;
        this.socialUserSearchRepository = socialUserSearchRepository;
    }

    @Override
    public SocialUser save(SocialUser socialUser) {
        log.debug("Request to save SocialUser : {}", socialUser);
        SocialUser result = socialUserRepository.save(socialUser);
        socialUserSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialUser> findAll(Pageable pageable) {
        log.debug("Request to get all SocialUsers");
        return socialUserRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SocialUser> findOne(Long id) {
        log.debug("Request to get SocialUser : {}", id);
        return socialUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialUser : {}", id);
        socialUserRepository.deleteById(id);
        socialUserSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialUser> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SocialUsers for query {}", query);
        return socialUserSearchRepository.search(queryStringQuery(query), pageable);    }
    
    
    @Override
    public Optional<SocialUser> findOneByEmailIgnoreCase(String email) {
    	return socialUserRepository.findOneByEmailIgnoreCase(email);
    }

	@Override
	public Optional<SocialUser> findOneByEmailIgnoreCaseAndProviderIgnoreCase(String email, String provider) {
		return this.socialUserRepository.findOneByEmailIgnoreCaseAndProviderIgnoreCase(email, provider);
	}
    
    
    
}
