package com.fafafashop.lyrics.web.rest;

import com.fafafashop.lyrics.domain.SocialUser;
import com.fafafashop.lyrics.domain.User;
import com.fafafashop.lyrics.security.jwt.JWTFilter;
import com.fafafashop.lyrics.security.jwt.TokenProvider;
import com.fafafashop.lyrics.security.providers.facebook.FacebookAccessToken;
import com.fafafashop.lyrics.security.providers.jwt.JwtSigningKeyResolverAdapter;
import com.fafafashop.lyrics.service.SocialUserService;
import com.fafafashop.lyrics.service.UserService;
import com.fafafashop.lyrics.service.dto.SocialUserDTO;
import com.fafafashop.lyrics.service.dto.UserDTO;
import com.fafafashop.lyrics.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final Logger log = LoggerFactory.getLogger(UserJWTController.class);
	
    private final TokenProvider tokenProvider;
    
    private final UserService userService;
    private final SocialUserService socialUserService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, SocialUserService socialUserService, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.socialUserService = socialUserService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    
    
    @PostMapping("/auth")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody SocialUserDTO socialUser) {
    	   	
    	if (!verifySocialUser(socialUser)) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	// Optional<SocialUser> userOptional = socialUserService.findOneByEmailIgnoreCase(socialUser.getEmail());
    	Optional<SocialUser> userOptional = socialUserService.findOneByEmailIgnoreCaseAndProviderIgnoreCase(socialUser.getEmail(), socialUser.getProvider());
    	
    	if (!userOptional.isPresent()) {
    		SocialUser user1 = new SocialUser();
    		
    		user1.setProvider( socialUser.getProvider());
    		user1.setSocialUserId( socialUser.getId() );
    		user1.setEmail(socialUser.getEmail());
    		user1.setName(socialUser.getName());
    		user1.setPhotoUrl(socialUser.getPhotoUrl());
    		user1.setFirstName(socialUser.getFirstName());
    		user1.setLastName(socialUser.getLastName());
    		user1.setAuthToken(socialUser.getAuthToken());
    		// user1.setIdToken(socialUser.getIdToken());
    		user1.setAuthorizationCode(socialUser.getAuthorizationCode());
    		
    		user1.setFacebook(socialUser.getFacebook()==null?null:socialUser.getFacebook().toString());
    		user1.setLinkedIn(socialUser.getLinkedIn()==null?null:socialUser.getLinkedIn().toString());
    		    		
    		socialUserService.save(user1);
    		
    		
    		Optional<User> user1a = userService.getUserByEmail(socialUser.getEmail());    		
    		if (!user1a.isPresent()) {
    		
	    		UserDTO userDTO = new UserDTO();
	    		userDTO.setActivated(true);
	    		Set<String> authorities = new HashSet<String>();
	    		authorities.add("ROLE_USER");
	    		userDTO.setAuthorities(authorities);
	    		userDTO.setCreatedBy("admin");
	    		userDTO.setCreatedDate(Instant.now());
	    		userDTO.setEmail(socialUser.getEmail());
	    		userDTO.setFirstName(socialUser.getFirstName());
	    		userDTO.setLastName(socialUser.getLastName());
	    		userDTO.setImageUrl(socialUser.getPhotoUrl());
	    		userDTO.setLangKey("en");
	    		userDTO.setLastModifiedBy("admin");
	    		userDTO.setLastModifiedDate(Instant.now());
	    		userDTO.setLogin(socialUser.getEmail());
	    		
	    		userService.createUser(userDTO);    		
    		}
    		
    	}

    	boolean rememberMe = true;
    	
        String token = tokenProvider.createToken(socialUser.getEmail(), "ROLE_USER", rememberMe);

        Authentication authentication = tokenProvider.getAuthentication(token);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    
    private boolean verifySocialUser( SocialUserDTO socialUser ) {
    	
    	if (socialUser==null) {
    		return false;
    	}
    	
    	String provider = socialUser.getProvider();
    	
    	if (provider == null || provider.trim().isEmpty()) {
    		return false;
    	}

    	if (provider.equalsIgnoreCase("Facebook")) {
    		if (socialUser.getAuthToken()==null) {
    			return false;
    		}
    		FacebookAccessToken token = new FacebookAccessToken(socialUser.getAuthToken());
    		return token.verify();
    	}
    	
    	
    	String idToken = socialUser.getIdToken();
    	
    	if (idToken != null) {
	    	try {
	    		SigningKeyResolverAdapter adapter = JwtSigningKeyResolverAdapter.get(socialUser.getProvider().toUpperCase());
	    		
	    		if (adapter == null) {
	    			return false;
	    		}
	    		
				Jwts.parserBuilder()
				.setSigningKeyResolver(adapter)		
				.build()
				.parseClaimsJws(idToken);
				
				return true;
			} catch (SignatureException e) {
				log.info("verifySocialUser failed", e);
				return false;
			} catch (ExpiredJwtException e) {
				log.info("verifySocialUser failed", e);
				return false;
			} catch (UnsupportedJwtException e) {
				log.info("verifySocialUser failed", e);
				return false;
			} catch (MalformedJwtException e) {
				log.info("verifySocialUser failed", e);
				return false;
			} catch (IllegalArgumentException e) {
				log.info("verifySocialUser failed", e);
				return false;
			}
    	}
    	
    	return true;
    }

    
    
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
