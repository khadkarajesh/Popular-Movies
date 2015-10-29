package com.example.popularmovies.activities.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.popularmovies.bus.EventBus;

import butterknife.ButterKnife;

/**
 * Base activity.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    /**
     * abstract method which returns id of layout in the form of R.layout.layout_name.
     *
     * @return id of layout in the form of R.layout.layout_name
     */
    protected abstract int getLayout();

    @Override
    protected void onResume() {
        EventBus.register(this);
        super.onResume();
    }

    /**
     * un-register event(Otto) on onPause() if any subclasses have registered for listening to events
     */
    @Override
    protected void onPause() {
        EventBus.unregister(this);
        super.onPause();
    }
}
