package com.example.popularmovies.activities.rest.service;

import com.example.popularmovies.activities.model.MovieComments;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface IMovieService {
    //http://api.themoviedb.org/3/movie/207703/reviews?api_key=3d9f6ef05faa3072ee2caf7fb6870964

    @GET("3/movie/{id}/reviews")
    Call<MovieComments> getComments(@Path("id") int id, @Query("api_key") String apiKey);

   /* @POST("http://api.themoviedb.org/3/movie/249070/reviews?api_key=3d9f6ef05faa3072ee2caf7fb6870964")
    Call<MovieComments> getComments();*/
}
