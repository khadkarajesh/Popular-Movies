package com.example.popularmovies.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.MovieActivity;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout mTilPassword;
    TextInputLayout mTilUsername;
    Button mBtnSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnSingIn = (Button) findViewById(R.id.login_btn_signin);
        mTilPassword = (TextInputLayout) findViewById(R.id.login_til_password);
        mTilUsername = (TextInputLayout) findViewById(R.id.login_til_password);
        mBtnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTilUsername.getEditText().getText().toString().trim();
                String password = mTilPassword.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(username)){
                    mTilUsername.setError("This field can not be empty");
                }
//                if(TextUtils.isEmpty(password)){
//                    mTilPassword.setError("This field can not be empty");
//                }
                if(username.equalsIgnoreCase("a") && password.equalsIgnoreCase("a")) {
                    MovieActivity.show(LoginActivity.this);
                }
            }
        });
    }
}
