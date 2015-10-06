package com.example.popularmovies.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.adapter.DuplicateMovieAdapter;
import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.data.Constants;
import com.example.popularmovies.fragment.base.BaseFragment;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;
import com.example.popularmovies.rest.model.MoviesInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemSelected;
import retrofit.Callback;
import retrofit.Response;


public class MovieDuplicateFragment extends BaseFragment {


    private List<Movie> movieArrayList;

    private int count = 0;

    private RetrofitManager retrofitManager;
    private String TAG = MovieDuplicateFragment.class.getSimpleName();

    @Bind(R.id.gridView1)
    GridView gridView;

    public MovieDuplicateFragment() {
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

        fetchMoviesFromWeb(1, getMovieCategories());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_movie_duplicate;
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

    private void setRecyclerView(List<Movie> movieArrayList) {
        gridView.setAdapter(new DuplicateMovieAdapter(getActivity(), movieArrayList));
       /* gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"called"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    @OnItemSelected(R.id.gridView1)
    public void onItemClick()
    {
        Toast.makeText(getActivity(),"called",Toast.LENGTH_SHORT).show();
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


}
