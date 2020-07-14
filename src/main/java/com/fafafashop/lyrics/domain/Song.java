package com.fafafashop.lyrics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Song.
 */
@Entity
@Table(name = "song")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "song")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "composed_by")
    private String composedBy;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "band")
    private String band;

    @Column(name = "year")
    private String year;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "lang")
    private String lang;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "entered_by")
    private String enteredBy;

    @Column(name = "last_modified")
    private ZonedDateTime lastModified;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Song name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Song description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposedBy() {
        return composedBy;
    }

    public Song composedBy(String composedBy) {
        this.composedBy = composedBy;
        return this;
    }

    public void setComposedBy(String composedBy) {
        this.composedBy = composedBy;
    }

    public String getArtist() {
        return artist;
    }

    public Song artist(String artist) {
        this.artist = artist;
        return this;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public Song album(String album) {
        this.album = album;
        return this;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getBand() {
        return band;
    }

    public Song band(String band) {
        this.band = band;
        return this;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getYear() {
        return year;
    }

    public Song year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCopyright() {
        return copyright;
    }

    public Song copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLang() {
        return lang;
    }

    public Song lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public Song audioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Song videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public Song enteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
        return this;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public Song lastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Song)) {
            return false;
        }
        return id != null && id.equals(((Song) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Song{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", composedBy='" + getComposedBy() + "'" +
            ", artist='" + getArtist() + "'" +
            ", album='" + getAlbum() + "'" +
            ", band='" + getBand() + "'" +
            ", year='" + getYear() + "'" +
            ", copyright='" + getCopyright() + "'" +
            ", lang='" + getLang() + "'" +
            ", audioUrl='" + getAudioUrl() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", enteredBy='" + getEnteredBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
