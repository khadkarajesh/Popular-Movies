package com.example.popularmovies.activities.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.activities.MovieDetailActivity;
import com.example.popularmovies.activities.fragment.MovieDetailFragment;
import com.example.popularmovies.activities.model.Movie;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rajesh on 9/18/15.
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

    public void setData(Movie movie) throws MalformedURLException {

        this.movie = movie;
      /*  Glide.with(getContext())
                .load(getImageUri(movie.posterPath))
                .placeholder(R.drawable.abc)
                .into(imgPoster);
        Log.e(TAG, "uri" + getImageUri(movie.posterPath));*/

        Picasso.with(getContext()).load(getImageUri(movie.posterPath)).placeholder(R.drawable.abc).into(imgPoster);
        tvMovieTitle.setText(movie.title);

    }

    public String getImageUri(String uri) {
        return IMAGE_POSTER_BASE_URL + "/" + uri;
    }

    @OnClick({R.id.img_movie_poster})
    public void onClick() {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.MOVIE_OBJECT, movie);
        context.startActivity(intent);
    }
}
