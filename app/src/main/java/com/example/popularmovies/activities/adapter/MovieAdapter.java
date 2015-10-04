package com.example.popularmovies.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.rest.model.Movie;
import com.example.popularmovies.activities.widget.SingleMovieItem;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rajesh on 9/18/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    List<Movie> movieArrayList;

    public MovieAdapter(List<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_all_movie_display, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder movieHolder, int i) {
        try {
            movieHolder.singleMovieItem.setData(movieArrayList.get(i));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.singleMovieItem)
        SingleMovieItem singleMovieItem;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
