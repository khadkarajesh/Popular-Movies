package com.example.popularmovies.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.MovieDetailActivity;
import com.example.popularmovies.rest.model.Movie;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * View for showing the movie poster and title for single movie.
 */
public class SingleMovieItem extends FrameLayout {


    private static final String TAG = SingleMovieItem.class.getSimpleName();
    private final String IMAGE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";
    private Movie movie;

    @Bind(R.id.img_movie_poster)
    SelectableRoundedImageView imgPoster;

    @Bind(R.id.card_view)
    CardView cardView;

    @Bind(R.id.tv_movie_title)
    TextView tvMovieTitle;

    Context context;

    public SingleMovieItem(Context context) {
        this(context, null);
    }

    public SingleMovieItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleMovieItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.single_movie_item, this, true);
        ButterKnife.bind(this, view);

        cardView.setPreventCornerOverlap(false);

    }

    /**
     * sets poster and title of the movie
     *
     * @param movie movie object containing the single movie details.
     * @throws MalformedURLException
     */
    public void setData(Movie movie) throws MalformedURLException {
        this.movie = movie;
        Picasso.with(getContext()).load(getImageUri(movie.posterPath)).placeholder(R.drawable.abc).into(imgPoster);
        tvMovieTitle.setText(movie.title);

    }

    /**
     * returns the complete url of movie poster by concatenating with base url.
     *
     * @param uri
     * @return
     */
    public String getImageUri(String uri) {
        return IMAGE_POSTER_BASE_URL + "/" + uri;
    }

    /**
     * for handling the click event of movie poster
     */
    @OnClick({R.id.img_movie_poster})
    public void onClick() {

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_OBJECT, movie);

        context.startActivity(intent);
    }
}
