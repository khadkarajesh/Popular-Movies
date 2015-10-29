package com.example.popularmovies.activities;

import android.os.Bundle;
import android.view.View;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.base.BaseActivity;
import com.example.popularmovies.fragment.MovieDetailFragment;
import com.example.popularmovies.rest.model.Movie;

/**
 * this activity is kept intentionally  for checking different view.
 */
public class TestActivity extends BaseActivity {



    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        //for making status bar transulent

        //get the single movie data passed form intent
        movie = getIntent().getParcelableExtra(MovieDetailActivity.MOVIE_OBJECT);

        // loads the fragment MovieFragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer,MovieDetailFragment.newInstance(movie,"tablet"))
                    .commit();
        }


    }


    @Override
    protected int getLayout() {
        return R.layout.activity_test_activities;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_activities, menu);
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
    }*/
}
