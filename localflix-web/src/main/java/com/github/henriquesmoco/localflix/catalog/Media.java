package com.github.henriquesmoco.localflix.catalog;

import javax.persistence.*;

@Entity
class Media {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String title;
    private String year;
    private String imbID;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private String posterUrl;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImbID() {
        return imbID;
    }

    public void setImbID(String imbID) {
        this.imbID = imbID;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String poster) {
        this.posterUrl = poster;
    }
}
