package com.fafafashop.lyrics.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A SocialUser.
 */
@Entity
@Table(name = "social_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "socialuser")
public class SocialUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "social_user_id")
    private String socialUserId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "auth_token")
    private String authToken;

    @Lob
    @Column(name = "id_token")
    private String idToken;

    @Column(name = "authorization_code")
    private String authorizationCode;

    /**
     * Contains the entire object returned from the Facebook API based on the fields you requested.\nOnly available for the Facebook provider.\nRefer to the Graph API for details: https:
     */
    @ApiModelProperty(value = "Contains the entire object returned from the Facebook API based on the fields you requested.\nOnly available for the Facebook provider.\nRefer to the Graph API for details: https:")
    @Lob
    @Column(name = "facebook")
    private String facebook;

    /**
     * Contains the entire object returned from the Linked In API based on the fields you requested.\nOnly available for the Linked In provider.\nRefer to the Linked In docs: https:
     */
    @ApiModelProperty(value = "Contains the entire object returned from the Linked In API based on the fields you requested.\nOnly available for the Linked In provider.\nRefer to the Linked In docs: https:")
    @Lob
    @Column(name = "linked_in")
    private String linkedIn;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public SocialUser provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public SocialUser socialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
        return this;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    public String getEmail() {
        return email;
    }

    public SocialUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public SocialUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public SocialUser photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public SocialUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SocialUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public SocialUser authToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public SocialUser idToken(String idToken) {
        this.idToken = idToken;
        return this;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public SocialUser authorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getFacebook() {
        return facebook;
    }

    public SocialUser facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public SocialUser linkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialUser)) {
            return false;
        }
        return id != null && id.equals(((SocialUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialUser{" +
            "id=" + getId() +
            ", provider='" + getProvider() + "'" +
            ", socialUserId='" + getSocialUserId() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", authToken='" + getAuthToken() + "'" +
            ", idToken='" + getIdToken() + "'" +
            ", authorizationCode='" + getAuthorizationCode() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", linkedIn='" + getLinkedIn() + "'" +
            "}";
    }
}
