package com.example.popularmovies.util;

/**
 * Created by rajesh on 9/21/15.
 */
public class Utility {

    private static final String IMAGE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String IMAGE_POSTER_SMALL_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public static String getImageUri(String uri) {
        return IMAGE_POSTER_BASE_URL + "/" + uri;
    }

    public static String getImageUriOfSmallSize(String uri) {
        return IMAGE_POSTER_BASE_URL + "/" + uri;
    }
}
