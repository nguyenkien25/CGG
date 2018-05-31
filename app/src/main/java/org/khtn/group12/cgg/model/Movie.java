package org.khtn.group12.cgg.model;

import java.io.Serializable;

public class Movie implements Serializable {
    public String id;
    public String name;
    public String image;
    public float rating;
    public String info;
    public String link_trailer;
    public String premiere;
    public String kind;
    public String directors;
    public String cast;
    public String time;
    public String language;

    public Movie() {
    }

    public Movie(String name, String image, float rating, String info, String link_trailer, String premiere, String kind,
                 String directors, String cast, String time, String language) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.info = info;
        this.link_trailer = link_trailer;
        this.premiere = premiere;
        this.kind = kind;
        this.directors = directors;
        this.cast = cast;
        this.time = time;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLink_trailer() {
        return link_trailer;
    }

    public void setLink_trailer(String link_trailer) {
        this.link_trailer = link_trailer;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
