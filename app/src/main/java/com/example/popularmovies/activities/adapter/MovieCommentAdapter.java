package com.example.popularmovies.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.rest.model.MovieComment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rajesh on 9/26/15.
 */
public class MovieCommentAdapter extends RecyclerView.Adapter<MovieCommentAdapter.CommentViewHolder> {

    private static final String TAG =MovieCommentAdapter.class.getSimpleName();
    private List<MovieComment> mMovieComments;

    public MovieCommentAdapter(List<MovieComment> movieComments) {
        this.mMovieComments = movieComments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_movie_comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        MovieComment movieComment = mMovieComments.get(position);

        holder.tvCommentName.setText(movieComment.author);
        holder.tvComment.setText(movieComment.content);
        Log.e(TAG, "comments:" + movieComment.content);
    }

    @Override
    public int getItemCount() {
        return mMovieComments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_commenter_name)
        TextView tvCommentName;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.iv_commenter_photo)
        ImageView ivCommenterPhoto;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
