package com.example.popularmovies.activities.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.activities.base.BaseActivity;
import com.example.popularmovies.activities.data.Constants;
import com.example.popularmovies.activities.model.Movie;
import com.example.popularmovies.activities.model.MovieComment;
import com.example.popularmovies.activities.model.MovieComments;
import com.example.popularmovies.activities.rest.service.IMovieService;
import com.example.popularmovies.activities.util.Utility;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieDetailActivity extends BaseActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_OBJECT = "data";


    @Bind(R.id.iv_play_movie)
    ImageView ivPlayMovie;

    @Bind(R.id.img_movie_poster)
    ImageView moviePoster;

    @Bind(R.id.tv_movie_title)
    TextView movieTitle;

    @Bind(R.id.tv_releasing_date)
    TextView releasingDate;

    @Bind(R.id.tv_overview)
    TextView overView;

    @Bind(R.id.rb_movie_rating)
    RatingBar ratingBar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

 /*   @Bind(R.id.rv_comments)
    RecyclerView rvComments;*/

    @Bind(R.id.ll_comments)
    LinearLayout llComments;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        movie = getIntent().getParcelableExtra(MovieDetailActivity.MOVIE_OBJECT);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle(movie.title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));


        setData();


        Picasso.with(this).load(Utility.getImageUri(movie.posterPath)).into(moviePoster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) moviePoster.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });

    }

    private void setData() {

        Picasso.with(this).load(Utility.getImageUri(movie.posterPath))
                .into(moviePoster);
        movieTitle.setText(movie.title);
        ratingBar.setRating(movie.voteAverage);
        overView.setText(movie.overview);

        getCommentsFromWeb();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_movie_detail_duplicate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.primary_dark);
        int primary = ContextCompat.getColor(this, R.color.primary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground(floatingActionButton, palette);
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(ContextCompat.getColor(this, android.R.color.white));
        int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(this, R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @OnClick({R.id.iv_play_movie})
    public void onClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "kl8F-8tR8to"));
        startActivity(intent);
    }

    private void getCommentsFromWeb() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMovieService iMovieService = retrofit.create(IMovieService.class);

        Call<MovieComments> movieCommentsCall = iMovieService.getComments(movie.id, Constants.API_KEY);
        movieCommentsCall.enqueue(new retrofit.Callback<MovieComments>() {
            @Override
            public void onResponse(Response<MovieComments> response) {
                Log.e(TAG, "success" + response.body().toString());
                Log.e(TAG, "value" + response.body().movieCommentList.get(0).content);

                List<MovieComment> comments = response.body().movieCommentList;
                showMovieComments(comments);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "failure failed" + t.getMessage());
            }
        });
    }

    private void showMovieComments(List<MovieComment> response) {
       /* if (response != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvComments.setLayoutManager(linearLayoutManager);
            rvComments.setAdapter(new MovieCommentAdapter(response));
        }*/

        if(response!=null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_movie_comments, null);
            TextView tvCommenterName = ButterKnife.findById(view, R.id.tv_commenter_name);
            TextView tvComment = ButterKnife.findById(view, R.id.tv_comment);
            for (MovieComment comment : response) {
                tvComment.setText(comment.content);
                tvCommenterName.setText(comment.author);
                llComments.addView(view);
            }
        }

    }

}
