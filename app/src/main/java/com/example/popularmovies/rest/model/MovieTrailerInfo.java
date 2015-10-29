package com.example.popularmovies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajesh on 10/1/15.
 */
public class MovieTrailerInfo {
    private static final String TAG = MovieTrailerInfo.class.getSimpleName();
    @SerializedName("results")
    public List<MovieTrailer> movieTrailers;
}
