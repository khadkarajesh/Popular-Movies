package com.example.popularmovies.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.example.popularmovies.data.MoviesContract;
import com.example.popularmovies.fragment.base.BaseFragment;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;
import com.example.popularmovies.util.Utility;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends BaseFragment {


    private ArrayList<Movie> movieArrayList;
    private final String MOVIE_DATA = "movie_data";

    private int count = 0;

    private RetrofitManager retrofitManager;
    private String TAG = MovieFragment.class.getSimpleName();

    @BindView(R.id.gridView1)
    GridView gridView;

    MovieAdapter duplicateMovieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get instance of retrofit manager class for network call
        retrofitManager = RetrofitManager.getInstance();

        //register the event bus for listening the movie categories change event
        EventBus.register(this);

        movieArrayList = new ArrayList<>();


        gridView.setDrawSelectorOnTop(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.post(new PopularMoviesEvent.MoviePosterSelectionEvent(movieArrayList.get(position)));
            }
        });

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            movieArrayList = savedInstanceState.getParcelableArrayList(MOVIE_DATA);
            setGridView(movieArrayList);
        } else {
            fetchData();
        }

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_movie;
    }

    /**
     * fetches the movies data from web and adds data to list {@link #movieArrayList}
     *
     * @param pageNumber       for getting the data of page number .
     * @param moviesCategories movie categories{popular, top_rated}
     */
    private void fetchMoviesFromWeb(int pageNumber, String moviesCategories) {
        Callback<List<Movie>> moviesInfoCallback = new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    movieArrayList.addAll(response.body());
                    if (count == 0) {
                        setGridView(movieArrayList);
                        count++;
                    } else {
                        duplicateMovieAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        };
        retrofitManager.getMoviesInfo(moviesCategories, pageNumber, BuildConfig.MOVIE_API_KEY, moviesInfoCallback);
    }

    /**
     * set grid view with movie's thumbnail.
     *
     * @param movieArrayList
     */
    private void setGridView(final List<Movie> movieArrayList) {
        duplicateMovieAdapter = new MovieAdapter(getActivity(), movieArrayList);
        gridView.setAdapter(duplicateMovieAdapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_DATA, movieArrayList);

    }

    /**
     * fetch the data according to the categories of movie.
     * if the categories is favourite fetches data from the database.
     * else fetches from the web
     */
    private void fetchData() {
        String categories = Utility.getMovieCategories(getActivity());
        if (categories.equals(getString(R.string.favourite_categories_value))) {
            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);

            movieArrayList.clear();
            Movie movie;
            while (cursor.moveToNext()) {
                movie = new Movie();
                movie.id = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ID));
                movie.overview = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                movie.voteAverage = cursor.getFloat(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_RATING));
                movie.posterPath = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
                movie.title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE));
                movie.releaseDate = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
                movieArrayList.add(movie);
            }
            setGridView(movieArrayList);
        } else {
            fetchMoviesFromWeb(1, categories);
        }
    }


    /**
     * handle the event of movie categories change and loads the UI with updated data
     * by making the network call according to categories.
     *
     * @param event
     */
    @Subscribe
    public void handlePreferenceChangeEvent(PopularMoviesEvent.PreferenceChangeEvent event) {
        if (event != null) {
            movieArrayList.clear();
            fetchData();
            if (movieArrayList.size() > 0) {
                duplicateMovieAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * for handling the event on pressing the favourite button of detail view.
     *
     * @param movieUnFavourite
     */
    @Subscribe
    public void handleMovieUnFavouriteEvent(PopularMoviesEvent.MovieUnFavourite movieUnFavourite) {
        fetchData();
    }


}
