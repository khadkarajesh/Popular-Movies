package com.example.popularmovies.view.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.R;
import com.example.popularmovies.base.activity.BaseActivity;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.example.popularmovies.data.service.MovieService;
import com.example.popularmovies.rest.BaseCallback;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;
import com.squareup.otto.Subscribe;

import java.util.List;

import retrofit2.Callback;

public class TestActivity extends BaseActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private MovieService mMovieService = new MovieService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Callback<List<Movie>> moviesInfoCallback = new BaseCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                if (!movies.isEmpty()) {
                    mMovieService.saveAll(movies);
                }
            }
        };
        RetrofitManager.getInstance().getMoviesInfo("popular", 1, BuildConfig.MOVIE_API_KEY, moviesInfoCallback);


        for (Movie movie : mMovieService.getAll()) {
            Log.d(TAG, "onCreate: " + movie.title);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Subscribe
    public void onEvent(PopularMoviesEvent.ServerErrorEvent errorEvent) {
        Log.d(TAG, "onEvent: hello nerd");
    }
}
