package com.example.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.popularmovies.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /**
     * get movie categories that is stored in the default sharedPreferences
     *
     * @return returns the movie categories{i-e popular/top_rated}
     */
    public static String getMovieCategories(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String movieCategories = sharedPreferences.getString(context.getString(R.string.movies_categories_key), context.getString(R.string.default_movies_categories));
        return movieCategories;
    }


    public  static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
