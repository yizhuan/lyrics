package com.fafafashop.lyrics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Lyrics.
 */
@Entity
@Table(name = "lyrics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lyrics")
public class Lyrics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_id")
    private Long songId;

    @Lob
    @Column(name = "lyrics")
    private String lyrics;

    @Column(name = "lang")
    private String lang;

    @Column(name = "author")
    private String author;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "is_translated")
    private Boolean isTranslated;

    @Column(name = "translated_by")
    private String translatedBy;

    @Column(name = "charset")
    private String charset;

    @Column(name = "last_modified")
    private ZonedDateTime lastModified;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSongId() {
        return songId;
    }

    public Lyrics songId(Long songId) {
        this.songId = songId;
        return this;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getLyrics() {
        return lyrics;
    }

    public Lyrics lyrics(String lyrics) {
        this.lyrics = lyrics;
        return this;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLang() {
        return lang;
    }

    public Lyrics lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAuthor() {
        return author;
    }

    public Lyrics author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public Lyrics copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Boolean isIsTranslated() {
        return isTranslated;
    }

    public Lyrics isTranslated(Boolean isTranslated) {
        this.isTranslated = isTranslated;
        return this;
    }

    public void setIsTranslated(Boolean isTranslated) {
        this.isTranslated = isTranslated;
    }

    public String getTranslatedBy() {
        return translatedBy;
    }

    public Lyrics translatedBy(String translatedBy) {
        this.translatedBy = translatedBy;
        return this;
    }

    public void setTranslatedBy(String translatedBy) {
        this.translatedBy = translatedBy;
    }

    public String getCharset() {
        return charset;
    }

    public Lyrics charset(String charset) {
        this.charset = charset;
        return this;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public Lyrics lastModified(ZonedDateTime lastModified) {
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
        if (!(o instanceof Lyrics)) {
            return false;
        }
        return id != null && id.equals(((Lyrics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lyrics{" +
            "id=" + getId() +
            ", songId=" + getSongId() +
            ", lyrics='" + getLyrics() + "'" +
            ", lang='" + getLang() + "'" +
            ", author='" + getAuthor() + "'" +
            ", copyright='" + getCopyright() + "'" +
            ", isTranslated='" + isIsTranslated() + "'" +
            ", translatedBy='" + getTranslatedBy() + "'" +
            ", charset='" + getCharset() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
