package com.example.popularmovies;


import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;

import com.example.popularmovies.view.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {
    private Button mBtnSignIn;
    private TextInputLayout mTilUsername;
    private TextInputLayout mTilPassword;
    @Before
    public void setUp(){
        Activity activity= Robolectric.setupActivity(LoginActivity.class);
        mBtnSignIn = (Button) activity.findViewById(R.id.login_btn_signin);
        mTilUsername = (TextInputLayout) activity.findViewById(R.id.login_til_username);
        mTilPassword = (TextInputLayout) activity.findViewById(R.id.login_til_password);
    }

    @Test
    public void loginSuccess(){
        mTilUsername.getEditText().setText("a");
        mTilPassword.getEditText().setText("a");
        mBtnSignIn.performClick();
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }

    @Test
    public void loginFailure(){
        mTilUsername.getEditText().setText(null);
        mTilPassword.getEditText().setText(null);
        mBtnSignIn.performClick();
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has not started",application.getNextStartedActivity(),is(nullValue()));
    }

}
