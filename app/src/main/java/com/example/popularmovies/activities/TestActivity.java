package com.example.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.R;
import com.example.popularmovies.rest.BaseCallback;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //RetrofitManager.getInstance().getMoviesInfo();

        Callback<List<Movie>> moviesInfoCallback = new BaseCallback<List<Movie>>() {

            @Override
            public void onSuccess(List<Movie> movies) {
                if (!movies.isEmpty()) {
                    Log.d(TAG, "onSuccess: " + movies.size());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, String message) {

            }
        };
        RetrofitManager.getInstance().getMoviesInfo("popular", 1, BuildConfig.MOVIE_API_KEY, moviesInfoCallback);

    }
}
