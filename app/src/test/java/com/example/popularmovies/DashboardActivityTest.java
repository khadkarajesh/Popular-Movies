package com.example.popularmovies;


import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.popularmovies.view.DashBoardActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DashboardActivityTest {
    private EditText mEdtUsername;
    private TextView mTvDisplay;
    @Before
    public void setUp(){
        Activity activity= Robolectric.setupActivity(DashBoardActivity.class);
        mEdtUsername= (EditText) activity.findViewById(R.id.dashboard_edt_user_name);
        mTvDisplay= (TextView) activity.findViewById(R.id.dashboard_tv_display);
    }

    @Test
    public void displayShouldShowTextAfterEditActionDone(){
        //mEdtUsername.seton
    }
}
