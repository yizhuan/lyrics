package com.fafafashop.lyrics.service.dto;

import com.fafafashop.lyrics.domain.SocialUser;

public class SocialUserDTO {

    private String provider;

    private String id;

    private String email;

    private String name;

    private String photoUrl;

    private String firstName;

    private String lastName;

    private String authToken;

    private String idToken;

    private String authorizationCode;

    private Object facebook;

    private Object linkedIn;

	public SocialUserDTO() {
		super();
	}
	
	public SocialUserDTO(SocialUser user) {
		super();
		
		this.provider = user.getProvider();
		this.id = user.getSocialUserId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.photoUrl = user.getPhotoUrl();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.authToken = user.getAuthToken();
		this.idToken = user.getIdToken();
		this.authorizationCode = user.getAuthorizationCode();
		this.facebook = user.getFacebook();
		this.linkedIn = user.getLinkedIn();
		
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public Object getFacebook() {
		return facebook;
	}

	public void setFacebook(Object facebook) {
		this.facebook = facebook;
	}

	public Object getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(Object linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	
	
    
	
}
