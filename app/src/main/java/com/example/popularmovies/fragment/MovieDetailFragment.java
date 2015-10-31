package com.example.popularmovies.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;
import com.example.popularmovies.data.Constants;
import com.example.popularmovies.data.MoviesContract;
import com.example.popularmovies.fragment.base.BaseFragment;
import com.example.popularmovies.rest.RetrofitManager;
import com.example.popularmovies.rest.model.Movie;
import com.example.popularmovies.rest.model.MovieComment;
import com.example.popularmovies.rest.model.MovieComments;
import com.example.popularmovies.rest.model.MovieTrailerInfo;
import com.example.popularmovies.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MovieDetailFragment extends BaseFragment {

    private static final String MOVIE_OBJECT = "movie_object";
    private static final String TABLET = "tablet";

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


    @Nullable
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Bind(R.id.ll_comments)
    LinearLayout llComments;

    @Bind(R.id.tv_comment_title)

    TextView tvCommentTitle;
    //Movie movie;
    RetrofitManager retrofitManager = null;

    List<MovieComment> comments;


    private Movie mMovie;

    private AppCompatActivity activity;
    private String TAG = MovieDetailFragment.class.getSimpleName();

    String trailerKey;


    public static MovieDetailFragment newInstance(Movie movie, String tablet) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_OBJECT, movie);
        args.putString(TABLET, tablet);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(MOVIE_OBJECT);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        //if movie doesn't contain the comment make comment textView invisible
        tvCommentTitle.setVisibility(View.GONE);

        //register the retrofit for network call
        retrofitManager = RetrofitManager.getInstance();

        setData();


    }


    /**
     * sets the detail of movie.
     */
    private void setData() {

        Picasso.with(getActivity()).load(Utility.getImageUri(mMovie.posterPath))
                .into(moviePoster);
        movieTitle.setText(mMovie.title);
        ratingBar.setRating(mMovie.voteAverage);
        overView.setText(mMovie.overview);
        releasingDate.setText(mMovie.releaseDate);

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor movieCursor = contentResolver.query(MoviesContract.MovieEntry.buildMovieUri(mMovie.id), null, null, null, null, null);

        if (movieCursor.getCount() > 0) {
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fav));
            favourite = true;
        }

        String movieId = Integer.toString(mMovie.id);


        String categories = Utility.getMovieCategories(getActivity());
        if (categories.equals(getString(R.string.favourite_categories_value))) {
            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieCommentEntry.buildCommentUri(mMovie.id), null, null, new String[]{movieId}, null);
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
            getCommentsFromWeb();
        }
    }


    @OnClick({R.id.iv_play_movie})
    public void onClick() {
        playTrailer(trailerKey);
    }

    @OnClick({R.id.fab})
    public void addFavourite(View view) {


        if (!favourite) {

            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fav));

            //for inserting the movie description to the movie table
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.id);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, mMovie.title);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, mMovie.releaseDate);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, mMovie.posterPath);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RATING, mMovie.voteAverage);
            contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, mMovie.overview);

            getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);


            ContentValues cv = null;
            //for inserting the comment of respective movie in comments table.
            if (comments != null) {
                for (MovieComment movieComment : comments) {
                    cv = new ContentValues();
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_ID, mMovie.id);
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_AUTHOR_NAME, movieComment.author);
                    cv.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_COMMENT, movieComment.content);
                    getActivity().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, cv);
                }

            }
            favourite = true;
        } else {
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_unfav));
            int id = getActivity().getContentResolver().delete(MoviesContract.MovieEntry.buildMovieUri(mMovie.id), null, null);
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
            // Verify the original intent will resolve to at least one activity
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
     * get comments of movie having specific id from web
     */
    private void getCommentsFromWeb() {
        Callback<MovieComments> callback = new Callback<MovieComments>() {
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
        retrofitManager.getComments(mMovie.id, Constants.API_KEY, callback);
        getTrailerKeyFromWeb();

    }

    /**
     * shows the comments on the view
     *
     * @param response
     */
    private void showMovieComments(List<MovieComment> response) {

        tvCommentTitle.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (MovieComment comment : response) {

            View view = layoutInflater.inflate(R.layout.layout_movie_comments, llComments, false);
            TextView tvCommenterName = ButterKnife.findById(view, R.id.tv_commenter_name);
            TextView tvComment = ButterKnife.findById(view, R.id.tv_comment);

            tvComment.setText(comment.content);
            tvCommenterName.setText(comment.author);

            llComments.addView(view);
        }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_movie_detail_tab;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_movie_detail, menu);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareMovieTrailerUrl();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareMovieTrailerUrl() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity()).setType("text/plain")
                .setText(Uri.parse(Constants.YOUTUBE_INTENT_BASE_URI + trailerKey).toString())
                .getIntent();
        if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }

    private void getTrailerKeyFromWeb() {
        retrofit.Callback<MovieTrailerInfo> movieTrailerInfoCallback = new retrofit.Callback<MovieTrailerInfo>() {
            @Override
            public void onResponse(Response<MovieTrailerInfo> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body().movieTrailers.size() > 0) {
                    trailerKey = response.body().movieTrailers.get(0).key;
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        retrofitManager.getTrailer(mMovie.id, Constants.API_KEY, movieTrailerInfoCallback);
    }
}
