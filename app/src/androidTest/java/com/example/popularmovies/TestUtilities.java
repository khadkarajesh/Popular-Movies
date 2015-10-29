package com.example.popularmovies;

import android.content.ContentValues;

import com.example.popularmovies.data.MoviesContract;

/**
 * Created by rajesh on 10/6/15.
 */
public class TestUtilities {

    public static ContentValues createMovieData(int movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, "Bagmati");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, "2015-10-20");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RATING, 7.8);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, " this is the horror movie");
        return contentValues;
    }

    public static ContentValues createMovieComments(long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_COMMENT, "this movie is asesome movie");
        contentValues.put(MoviesContract.MovieCommentEntry.COLUMN_MOVIE_ID, movieId);
        return contentValues;
    }

    public static ContentValues createUpdatedMovieData(int movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE, "BisnuMati");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, "2015-10-20");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_RATING, 7.8);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, " this is the action movie");
        return contentValues;
    }


}
