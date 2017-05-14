package com.example.popularmovies.rest;

import com.example.popularmovies.data.Constants;
import com.example.popularmovies.rest.converters.ResponseEnvelopeConverterFactory;
import com.example.popularmovies.rest.model.Movie;
import com.example.popularmovies.rest.model.MovieComments;
import com.example.popularmovies.rest.model.MovieTrailerInfo;
import com.example.popularmovies.rest.service.IMovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for making the network call for specific url,getting response.
 */
public class RetrofitManager {

    public static Retrofit retrofit = null;
    public static IMovieService iMovieService = null;
    public static RetrofitManager retrofitManager = null;

    private RetrofitManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_BASE_URL)
                .addConverterFactory(new ResponseEnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iMovieService = retrofit.create(IMovieService.class);
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    /**
     * get movies information
     *
     * @param categories categories {popular,top_rated}
     * @param page       for getting data of specific page number
     * @param apiKey     api key that is provided by themoviedb.com
     * @param callback   callback for getting response
     */
    public void getMoviesInfo(String categories, int page, String apiKey, Callback<List<Movie>> callback) {
        Call<List<Movie>> moviesInfoCall = iMovieService.getMoviesInfo(categories, page, apiKey);
        moviesInfoCall.enqueue(callback);
    }

    /**
     * gets comment of single movie having specific movieId
     *
     * @param movieId  id of movie
     * @param apiKey   api key that is provided by themoviedb.com
     * @param callback callback for getting response
     */

    public void getComments(int movieId, String apiKey, Callback<MovieComments> callback) {
        Call<MovieComments> movieCommentsCall = iMovieService.getComments(movieId, apiKey);
        movieCommentsCall.enqueue(callback);
    }

    /**
     * gets the key for movie trailer
     *
     * @param movieId  id of movie
     * @param apiKey   api key that is provided by themoviedb.com
     * @param callback callback for getting response
     */

    public void getTrailer(int movieId, String apiKey, Callback<MovieTrailerInfo> callback) {
        Call<MovieTrailerInfo> movieCommentsCall = iMovieService.getMovieTrailer(movieId, apiKey);
        movieCommentsCall.enqueue(callback);
    }
}
