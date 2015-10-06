package com.example.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.base.BaseActivity;
import com.example.popularmovies.fragment.MovieFragment;

import butterknife.Bind;


/**
 * loads  the fragment {@link MovieFragment} which shows the grid view with movie poster.
 */

public class MovieActivity extends BaseActivity {
    private static final String TAG = MovieActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, TestActivity.class));
        //set toolbar.
        setSupportActionBar(toolbar);

        // loads the fragment MovieFragment
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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
