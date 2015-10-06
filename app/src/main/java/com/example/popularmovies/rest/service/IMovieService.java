package com.example.popularmovies.rest.service;

import com.example.popularmovies.rest.model.MovieTrailerInfo;
import com.example.popularmovies.rest.model.MoviesInfo;
import com.example.popularmovies.rest.model.MovieComments;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;



public interface IMovieService {
    //http://api.themoviedb.org/3/movie/331214/videos?api_key=3d9f6ef05faa3072ee2caf7fb6870964

    @GET("3/movie/{categories}")
    Call<MoviesInfo> getMoviesInfo(@Path("categories") String categories, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<MovieComments> getComments(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<MovieTrailerInfo> getMovieTrailer(@Path("id") int id, @Query("api_key") String apiKey);



   /* @POST("http://api.themoviedb.org/3/movie/331214/reviews?api_key=3d9f6ef05faa3072ee2caf7fb6870964")
    Call<MovieComments> getComments();*/
}
