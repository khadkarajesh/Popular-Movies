package com.example.popularmovies.activities.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.model.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();
    @Bind(R.id.img_movie_poster)
    ImageView moviePoster;

    @Bind(R.id.img_mini_poster)
    ImageView imgMiniPoster;

    @Bind(R.id.tv_overview)
    TextView overView;

    @Bind(R.id.rb_movie_rating)
    RatingBar ratingBar;

    @Bind(R.id.toolbar)Toolbar toolbar;

    public static final String MOVIE_OBJECT = "data";

    private Movie movie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail_duplicate, container, false);
        ButterKnife.bind(this, view);




/*
        //movie = getArguments().getParcelable(MOVIE_OBJECT);

        Glide.with(getActivity()).load(Utility.getImageUri(movie.posterPath))
                .centerCrop()
                .into(moviePoster);

        Glide.with(getActivity()).load(Utility.getImageUriOfSmallSize(movie.posterPath)).into(imgMiniPoster);

        ratingBar.setRating(3.5f);

        overView.setText(movie.overview);*/


        return view;
    }

}
