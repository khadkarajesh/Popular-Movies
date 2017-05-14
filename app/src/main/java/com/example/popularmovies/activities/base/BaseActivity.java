package com.example.popularmovies.activities.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;

import static com.example.popularmovies.bus.PopularMoviesEvent.EVENT_SERVER_ERROR;

/**
 * Base activity.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Object busEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        busEventListener = new Object() {
            @Subscribe
            public void onEvent(PopularMoviesEvent popularMoviesEvent) {
                switch (popularMoviesEvent.mAction) {
                    case EVENT_SERVER_ERROR:
                        String message = ((PopularMoviesEvent.ServerErrorEvent) popularMoviesEvent).message;
                        showError(message);
                        break;
                }
            }

        };
        EventBus.register(busEventListener);
        EventBus.register(this);
    }

    /**
     * abstract method which returns id of layout in the form of R.layout.layout_name.
     *
     * @return id of layout in the form of R.layout.layout_name
     */
    protected abstract int getLayout();

    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Failure");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        EventBus.unregister(busEventListener);
        EventBus.unregister(this);
        super.onDestroy();
    }
}
