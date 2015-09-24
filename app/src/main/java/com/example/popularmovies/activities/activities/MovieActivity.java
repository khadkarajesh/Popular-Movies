package com.example.popularmovies.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.activities.base.BaseActivity;
import com.example.popularmovies.activities.fragment.MovieFragment;

import butterknife.Bind;

public class MovieActivity extends BaseActivity {
    private static final String TAG = MovieActivity.class.getSimpleName();

    private static final int NO_OF_COLUMN = 2;
    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    private static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
    private static final String API_KEY = "3d9f6ef05faa3072ee2caf7fb6870964";


    private static final String POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg";

   /* @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    GridLayoutManager gridLayoutManager;*/

    @Bind(R.id.toolbar)Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        if (false) {
            startActivity(new Intent(this, TestActivity.class));
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, new MovieFragment())
                    .commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
