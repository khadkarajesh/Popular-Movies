package com.example.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.rest.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rajesh on 10/4/15.
 */
public class MovieAdapter extends BaseAdapter {

    List<Movie> movieArrayList;
    LayoutInflater inflater;
    private final String IMAGE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";
    private Context mContext;


    public MovieAdapter(Context context, List<Movie> movieArrayList) {
        this.mContext = context;
        this.movieArrayList = movieArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = movieArrayList.get(position);
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.single_movie_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.tvMovieTitle.setText(movie.title);
        Picasso.with(mContext).load(getImageUri(movie.posterPath)).placeholder(R.drawable.abc).into(holder.imgPoster);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;

        @BindView(R.id.img_movie_poster)
        ImageView imgPoster;

        @BindView(R.id.card_view)
        CardView cardView;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public String getImageUri(String uri) {
        return IMAGE_POSTER_BASE_URL + "/" + uri;
    }
}
