package com.example.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.base.BaseActivity;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.example.popularmovies.data.MovieDbHelper;
import com.example.popularmovies.fragment.MovieDetailFragment;
import com.example.popularmovies.fragment.MovieDuplicateFragment;
import com.example.popularmovies.fragment.MovieFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;


/**
 * loads  the fragment {@link MovieFragment} which shows the grid view with movie poster.
 */

public class MovieActivity extends BaseActivity {
    private static final String TAG = MovieActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @Bind(R.id.movie_detail_container)
    FrameLayout detailContainer;

    private String DETAIL_FRAGMENT_TAG = "detail_fragment";

    MovieDuplicateFragment movieDuplicateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set toolbar.
        setSupportActionBar(toolbar);

        MovieDbHelper movieDbHelper = new MovieDbHelper(this);
        movieDbHelper.getWritableDatabase();

        if (savedInstanceState != null) {
            movieDuplicateFragment = (MovieDuplicateFragment) getFragmentManager().getFragment(savedInstanceState, "mContent");
            getFragmentManager().beginTransaction().replace(R.id.movie_container, movieDuplicateFragment).commit();
        } else {
            movieDuplicateFragment = new MovieDuplicateFragment();
            getFragmentManager().beginTransaction().add(R.id.movie_container, movieDuplicateFragment).commit();
        }


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_movie;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Subscribe
    public void handleMoviePosterSelectionEvent(PopularMoviesEvent.MoviePosterSelectionEvent moviePosterSelectionEvent) {
        if (detailContainer != null) {
            if (moviePosterSelectionEvent.movie != null && detailContainer != null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, MovieDetailFragment.newInstance(moviePosterSelectionEvent.movie, "tablet"), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
            Log.e(TAG, "ifFragmentCalled");
        } else {
            Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_OBJECT, moviePosterSelectionEvent.movie);
            startActivity(intent);
            Log.e(TAG, "elseFragmentCalled");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getFragmentManager().putFragment(outState, "mContent", movieDuplicateFragment);


    }

}
