package com.example.popularmovies.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.base.BaseActivity;
import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.example.popularmovies.data.Constants;
import com.example.popularmovies.data.MoviesContract;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;
import com.example.popularmovies.rest.model.MovieComment;
import com.example.popularmovies.rest.model.MovieComments;
import com.example.popularmovies.rest.model.MovieTrailer;
import com.example.popularmovies.rest.model.MovieTrailerInfo;
import com.example.popularmovies.util.Utility;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * shows the details{movie title,release date, rating,comments,trailer via intent} of single movie.
 */
public class MovieDetailActivity extends BaseActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_OBJECT = "data";

    private Boolean favourite = false;

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

    @Bind(R.id.ll_comments)
    LinearLayout llComments;

    @Nullable
    @Bind(R.id.ll_trailers)
    LinearLayout llTrailers;

    @Bind(R.id.tv_comment_title)
    TextView tvCommentTitle;

    @Nullable
    @Bind(R.id.tv_trailer_title)
    TextView tvTrailerTitle;

    Movie movie;
    RetrofitManager retrofitManager = null;

    List<MovieComment> comments;
    List<MovieTrailer> trailers;

    ColorStateList colorList;

    String trailerKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for making status bar transulent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //get the single movie data passed form intent
        movie = getIntent().getParcelableExtra(MovieDetailActivity.MOVIE_OBJECT);

        //set the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle(movie.title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        //if movie doesn't contain the comment make comment textView invisible
        tvCommentTitle.setVisibility(View.GONE);
        tvTrailerTitle.setVisibility(View.GONE);

        //register the retrofit for network call
        retrofitManager = RetrofitManager.getInstance();


        setData();

        getPalette();

    }


    private void getPalette() {

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

    /**
     * sets the detail of movie.
     */
    private void setData() {

        Picasso.with(this).load(Utility.getImageUri(movie.posterPath))
                .into(moviePoster);
        movieTitle.setText(movie.title);
        ratingBar.setRating(movie.voteAverage);
        overView.setText(movie.overview);
        releasingDate.setText(movie.releaseDate);

        ContentResolver contentResolver = getContentResolver();
        Cursor movieCursor = contentResolver.query(MoviesContract.MovieEntry.buildMovieUri(movie.id), null, null, null, null, null);

        if (movieCursor.getCount() > 0) {
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));
            favourite = true;
        }

        String movieId = Integer.toString(movie.id);


        String categories = Utility.getMovieCategories(this);
        if (categories.equals(getString(R.string.favourite_categories_value))) {
            Cursor cursor = getContentResolver().query(MoviesContract.MovieCommentEntry.buildCommentUri(movie.id), null, null, new String[]{movieId}, null);
            MovieComment movieComment;
            if (cursor.getCount() > 0) {
                comments = new ArrayList<>();
                while (cursor.moveToNext()) {
                    movieComment = new MovieComment();
                    movieComment.author = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieCommentEntry.COLUMN_AUTHOR_NAME));
                    movieComment.content = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_COMMENT));

                    comments.add(movieComment);
                }
                showMovieComments(comments);
            }
        } else {
            getDataFromWeb();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_moviedetail;
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
        if (id == R.id.action_share) {
            shareMovieTrailerUrl();
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
        colorList = ColorStateList.valueOf(vibrantColor);
        fab.setBackgroundTintList(colorList);
    }

    @OnClick({R.id.iv_play_movie})
    public void onClick() {
        if (Utility.isNetworkAvailable(this)) {
            playTrailer(trailerKey);
        }
    }

    @OnClick({R.id.fab})
    public void addFavourite(View view) {


        if (!favourite) {

            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav));

            //for inserting the movie description to the movie table
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movie.id);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.title);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.releaseDate);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.posterPath);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RATING, movie.voteAverage);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.overview);

            getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);


            ContentValues cv = null;
            //for inserting the comment of respective movie in comments table.
            if (comments != null) {
                for (MovieComment movieComment : comments) {
                    cv = new ContentValues();
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_ID, movie.id);
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_AUTHOR_NAME, movieComment.author);
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_COMMENT, movieComment.content);
                    getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, cv);
                }
            }

            favourite = true;
        } else {
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unfav));
            int id = getContentResolver().delete(MoviesContract.MovieEntry.buildMovieUri(movie.id), null, null);
            EventBus.post(new PopularMoviesEvent.MovieUnFavourite());
            favourite = false;
        }


    }

    /**
     * opens the youtube application via intent
     *
     * @param key
     */
    private void playTrailer(String key) {
        if (key != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_INTENT_BASE_URI + key));
            startActivity(intent);
        }
    }

    /**
     * get comments of movie having specific id from web
     */
    private void getDataFromWeb() {

        retrofit.Callback<MovieComments> callback = new retrofit.Callback<MovieComments>() {
            @Override
            public void onResponse(Response<MovieComments> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    comments = response.body().movieCommentList;
                    if (response.body().movieCommentList.size() > 0) {
                        showMovieComments(comments);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        retrofitManager.getComments(movie.id, Constants.API_KEY, callback);

        getTrailerKeyFromWeb();
    }

    /**
     * shows the comments on the view
     *
     * @param response
     */
    private void showMovieComments(List<MovieComment> response) {

        tvCommentTitle.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (MovieComment comment : response) {

            View view = layoutInflater.inflate(R.layout.layout_movie_comments, llComments, false);
            TextView tvCommenterName = ButterKnife.findById(view, R.id.tv_commenter_name);
            TextView tvComment = ButterKnife.findById(view, R.id.tv_comment);

            tvComment.setText(comment.content);
            tvCommenterName.setText(comment.author);

            llComments.addView(view);
        }


    }


    /**
     * for sharing the youtube url to the social media or others.
     */
    private void shareMovieTrailerUrl() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain")
                .setText(Uri.parse(Constants.YOUTUBE_INTENT_BASE_URI + trailerKey).toString())
                .getIntent();
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }

    /**
     * fetch the movies trailer key from the web
     */
    private void getTrailerKeyFromWeb() {
        retrofit.Callback<MovieTrailerInfo> movieTrailerInfoCallback = new retrofit.Callback<MovieTrailerInfo>() {
            @Override
            public void onResponse(Response<MovieTrailerInfo> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body().movieTrailers.size() > 0) {
                    trailerKey = response.body().movieTrailers.get(0).key;
                    showMovieTrailer(response.body().movieTrailers);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        retrofitManager.getTrailer(movie.id, Constants.API_KEY, movieTrailerInfoCallback);
    }

    /**
     * displays the movie tailer icon and trailer name.
     *
     * @param trailers
     */
    private void showMovieTrailer(List<MovieTrailer> trailers) {
        tvTrailerTitle.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < trailers.size(); i++) {

            View view = layoutInflater.inflate(R.layout.layout_movie_trailers, llComments, false);
            LinearLayout llTrailerWrapper = ButterKnife.findById(view, R.id.ll_trailer_wrapper);


            //add image view containing play icon for movie trailers.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
            layoutParams.rightMargin = 22;
            ImageView ivPlayIcon = new ImageView(this);
            ivPlayIcon.setTag(trailers.get(i));
            ivPlayIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_play));
            ivPlayIcon.setLayoutParams(layoutParams);

            ivPlayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieTrailer movieTrailer = (MovieTrailer) v.getTag();
                    playTrailer(movieTrailer.key);
                }
            });


            //text view for showing the trailer name.
            LinearLayout.LayoutParams paramsTvTrailer = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView tvTrailer = new TextView(this);
            tvTrailer.setText("trailer " + i++);
            tvTrailer.setGravity(Gravity.CENTER_VERTICAL);


            llTrailerWrapper.addView(ivPlayIcon, layoutParams);
            llTrailerWrapper.addView(tvTrailer, paramsTvTrailer);

            llTrailers.addView(llTrailerWrapper);
        }
    }


}
