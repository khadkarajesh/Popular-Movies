package com.example.popularmovies.activities.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajesh on 10/1/15.
 */
public class MovieTrailerInfo {
    @SerializedName("results")
    public List<MovieTrailer> movieTrailers;

    public class MovieTrailer {
        @SerializedName("key")
        public String key;
    }
}
