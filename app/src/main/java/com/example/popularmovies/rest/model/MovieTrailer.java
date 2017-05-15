package com.example.popularmovies.rest.model;

import android.util.Log;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.rest.RetrofitManager;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rajesh on 10/30/15.
 */
public class MovieTrailer {
    private static final String TAG =MovieTrailer.class.getSimpleName();
    @SerializedName("key")
    public String key;
    private RetrofitManager retrofitManager;

    public MovieTrailer()
    {
        retrofitManager=RetrofitManager.getInstance();
    }

    public String getMovieTrailerKey(int movieId) {
        final String[] result = {null};
       Callback<MovieTrailerInfo> movieTrailerInfoCallback = new Callback<MovieTrailerInfo>() {

           @Override
           public void onResponse(Call<MovieTrailerInfo> call, Response<MovieTrailerInfo> response) {
               if (response.isSuccessful() && response.body().movieTrailers.size() > 0) {
                   Log.e(TAG, "key" + response.body().movieTrailers.get(0).key);
                   //playTrailer(response.body().movieTrailers.getById(0).key);
                   result[0] = response.body().movieTrailers.get(0).key;

               }
           }

           @Override
           public void onFailure(Call<MovieTrailerInfo> call, Throwable t) {

           }
       };
        retrofitManager.getTrailer(movieId, BuildConfig.MOVIE_API_KEY, movieTrailerInfoCallback);
        return result[0];
    }
}