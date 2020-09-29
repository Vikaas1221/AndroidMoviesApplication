package com.example.moviesdbapp.Model;

import java.io.Serializable;

public class Movie implements Serializable
{
    private String originalTitle;
    private String movieImage;
    private String userRating;
    private String RelaseDate;
    private String overView;
    private String id;



    private String type;

    public Movie(String originalTitle,String movieImage,String userRating,String RelaseDate,String overView,String id)
    {
        this.originalTitle=originalTitle;
        this.movieImage=movieImage;
        this.userRating=userRating;
        this.RelaseDate=RelaseDate;
        this.overView=overView;
        this.id=id;
    }
    public Movie()
    {

    }
    public Movie(String originalTitle)
    {
        this.originalTitle=originalTitle;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getRelaseDate() {
        return RelaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        RelaseDate = relaseDate;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
