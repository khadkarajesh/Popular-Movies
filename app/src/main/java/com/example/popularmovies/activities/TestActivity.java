package com.example.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.base.BaseActivity;
import com.example.popularmovies.fragment.MovieDuplicateFragment;

import butterknife.Bind;

/**
 * this activity is kept intentionally  for checking different view.
 */
public class TestActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //set toolbar.
        setSupportActionBar(toolbar);

        // loads the fragment MovieFragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, new MovieDuplicateFragment())
                    .commit();
        }


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test_activities;
    }

    @Override
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
    }
}
