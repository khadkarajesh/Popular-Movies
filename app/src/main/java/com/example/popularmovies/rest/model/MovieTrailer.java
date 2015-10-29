package com.example.popularmovies.rest.model;

import android.util.Log;

import com.example.popularmovies.data.Constants;
import com.example.popularmovies.rest.RetrofitManager;
import com.google.gson.annotations.SerializedName;

import retrofit.Response;
import retrofit.Retrofit;

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
        retrofit.Callback<MovieTrailerInfo> movieTrailerInfoCallback = new retrofit.Callback<MovieTrailerInfo>() {
            @Override
            public void onResponse(Response<MovieTrailerInfo> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body().movieTrailers.size() > 0) {
                    Log.e(TAG, "key" + response.body().movieTrailers.get(0).key);
                    //playTrailer(response.body().movieTrailers.get(0).key);
                    result[0] = response.body().movieTrailers.get(0).key;

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        retrofitManager.getTrailer(movieId, Constants.API_KEY, movieTrailerInfoCallback);
        return result[0];
    }
}