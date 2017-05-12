package com.example.popularmovies.rest.service;

import com.example.popularmovies.rest.model.MovieComments;
import com.example.popularmovies.rest.model.MovieTrailerInfo;
import com.example.popularmovies.rest.model.MoviesInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IMovieService {
    @GET("3/movie/{categories}")
    Call<MoviesInfo> getMoviesInfo(@Path("categories") String categories, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<MovieComments> getComments(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<MovieTrailerInfo> getMovieTrailer(@Path("id") int id, @Query("api_key") String apiKey);
}
