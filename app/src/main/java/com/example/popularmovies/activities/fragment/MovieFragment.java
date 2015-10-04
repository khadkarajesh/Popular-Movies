package com.example.popularmovies.activities.fragment;


import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.adapter.MovieAdapter;
import com.example.popularmovies.activities.bus.EventBus;
import com.example.popularmovies.activities.bus.PopularMoviesEvent;
import com.example.popularmovies.activities.data.Constants;
import com.example.popularmovies.activities.fragment.base.BaseFragment;
import com.example.popularmovies.activities.rest.RetrofitManager;
import com.example.popularmovies.activities.rest.model.Movie;
import com.example.popularmovies.activities.rest.model.MoviesInfo;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;

/**
 * A simple class which shows the movie poster in the grid view with movie name.
 */
public class MovieFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    GridLayoutManager gridLayoutManager;

    private static final int NO_OF_COLUMN = 2;

    private List<Movie> movieArrayList;

    private RetrofitManager retrofitManager;

    private String TAG = MovieFragment.class.getSimpleName();

    private MovieAdapter movieAdapter;
    private int count = 0;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get instance of retrofit manager class for network call
        retrofitManager = RetrofitManager.getInstance();

        //register the event bus for listening the movie categories change event
        EventBus.register(this);

        movieArrayList = new ArrayList<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_movie;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchMoviesFromWeb(1, getMovieCategories());
    }

    /**
     * fetches the movies data from web and adds data to list {@link #movieArrayList}
     *
     * @param pageNumber       for getting the data of page number .
     * @param moviesCategories movie categories{popular, top_rated}
     */
    private void fetchMoviesFromWeb(int pageNumber, String moviesCategories) {
        Callback<MoviesInfo> moviesInfoCallback = new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Response<MoviesInfo> response) {

                if (response.isSuccess()) {
                    movieArrayList.addAll(response.body().movieList);
                    if (count == 0) {
                        setRecyclerView(movieArrayList);
                        count++;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "error" + t.getMessage());
            }
        };
        retrofitManager.getMoviesInfo(moviesCategories, pageNumber, Constants.API_KEY, moviesInfoCallback);
    }


    /**
     * set's the recycler view
     *
     * @param movies
     */
    private void setRecyclerView(List<Movie> movies) {
        gridLayoutManager = new GridLayoutManager(getActivity(), NO_OF_COLUMN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });
        movieAdapter = new MovieAdapter(movies);
        recyclerView.setAdapter(movieAdapter);
    }

    /**
     * get movie categories that is stored in the default sharedPreferences
     *
     * @return returns the movie categories{i-e popular/top_rated}
     */
    private String getMovieCategories() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieCategories = sharedPreferences.getString(getString(R.string.movies_categories_key), getString(R.string.default_movies_categories));
        return movieCategories;
    }

    /**
     * handle the event of movie categories change and loads the UI with updated data
     * by making the network call according to categories.
     *
     * @param event
     */
    @Subscribe
    public void handlePreferenceChangeEvent(PopularMoviesEvent.PreferenceChangeEvent event) {
        movieArrayList.clear();
        fetchMoviesFromWeb(1, getMovieCategories());
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //un-register the event bus
        EventBus.unregister(this);
    }
}
