package com.fafafashop.lyrics.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.fafafashop.lyrics.domain.Song} entity. This class is used
 * in {@link com.fafafashop.lyrics.web.rest.SongResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /songs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SongCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter composedBy;

    private StringFilter artist;

    private StringFilter album;

    private StringFilter band;

    private StringFilter year;

    private StringFilter copyright;

    private StringFilter lang;

    private StringFilter audioUrl;

    private StringFilter videoUrl;

    private StringFilter enteredBy;

    private ZonedDateTimeFilter lastModified;

    public SongCriteria() {
    }

    public SongCriteria(SongCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.composedBy = other.composedBy == null ? null : other.composedBy.copy();
        this.artist = other.artist == null ? null : other.artist.copy();
        this.album = other.album == null ? null : other.album.copy();
        this.band = other.band == null ? null : other.band.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.copyright = other.copyright == null ? null : other.copyright.copy();
        this.lang = other.lang == null ? null : other.lang.copy();
        this.audioUrl = other.audioUrl == null ? null : other.audioUrl.copy();
        this.videoUrl = other.videoUrl == null ? null : other.videoUrl.copy();
        this.enteredBy = other.enteredBy == null ? null : other.enteredBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
    }

    @Override
    public SongCriteria copy() {
        return new SongCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getComposedBy() {
        return composedBy;
    }

    public void setComposedBy(StringFilter composedBy) {
        this.composedBy = composedBy;
    }

    public StringFilter getArtist() {
        return artist;
    }

    public void setArtist(StringFilter artist) {
        this.artist = artist;
    }

    public StringFilter getAlbum() {
        return album;
    }

    public void setAlbum(StringFilter album) {
        this.album = album;
    }

    public StringFilter getBand() {
        return band;
    }

    public void setBand(StringFilter band) {
        this.band = band;
    }

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getCopyright() {
        return copyright;
    }

    public void setCopyright(StringFilter copyright) {
        this.copyright = copyright;
    }

    public StringFilter getLang() {
        return lang;
    }

    public void setLang(StringFilter lang) {
        this.lang = lang;
    }

    public StringFilter getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(StringFilter audioUrl) {
        this.audioUrl = audioUrl;
    }

    public StringFilter getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(StringFilter videoUrl) {
        this.videoUrl = videoUrl;
    }

    public StringFilter getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(StringFilter enteredBy) {
        this.enteredBy = enteredBy;
    }

    public ZonedDateTimeFilter getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTimeFilter lastModified) {
        this.lastModified = lastModified;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SongCriteria that = (SongCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(composedBy, that.composedBy) &&
            Objects.equals(artist, that.artist) &&
            Objects.equals(album, that.album) &&
            Objects.equals(band, that.band) &&
            Objects.equals(year, that.year) &&
            Objects.equals(copyright, that.copyright) &&
            Objects.equals(lang, that.lang) &&
            Objects.equals(audioUrl, that.audioUrl) &&
            Objects.equals(videoUrl, that.videoUrl) &&
            Objects.equals(enteredBy, that.enteredBy) &&
            Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        composedBy,
        artist,
        album,
        band,
        year,
        copyright,
        lang,
        audioUrl,
        videoUrl,
        enteredBy,
        lastModified
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SongCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (composedBy != null ? "composedBy=" + composedBy + ", " : "") +
                (artist != null ? "artist=" + artist + ", " : "") +
                (album != null ? "album=" + album + ", " : "") +
                (band != null ? "band=" + band + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (copyright != null ? "copyright=" + copyright + ", " : "") +
                (lang != null ? "lang=" + lang + ", " : "") +
                (audioUrl != null ? "audioUrl=" + audioUrl + ", " : "") +
                (videoUrl != null ? "videoUrl=" + videoUrl + ", " : "") +
                (enteredBy != null ? "enteredBy=" + enteredBy + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            "}";
    }

}
