package com.example.popularmovies.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.popularmovies.R;

public class DashBoardActivity extends AppCompatActivity {
    private EditText mEdtUsername;
    private TextView mTvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mEdtUsername= (EditText) findViewById(R.id.dashboard_edt_user_name);
        mTvDisplay= (TextView) findViewById(R.id.dashboard_tv_display);

        mEdtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvDisplay.setText(s);
            }
        });
    }
}
