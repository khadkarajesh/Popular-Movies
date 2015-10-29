package com.example.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class MoviesContract {

    //
    public static final String CONTENT_AUTHORITY = "com.example.popularmovies";

    //
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String MOVIE_PATH = "movies";
    public static final String MOVIE_COMMENTS_PATH = "comments";

    /* Inner class that defines the table contents of the movies table */
    public static final class MovieEntry implements BaseColumns {


        //movies table name
        public static final String TABLE_NAME = "movies";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH).build();


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;


        //column for storing the movie id provided by api
        public static final String COLUMN_MOVIE_ID = "movie_id";

        //column for storing the title of the movie
        public static final String COLUMN_MOVIE_TITLE = "title";

        // column for storing the release date of movie
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";

        //column for storing the movie rating
        public static final String COLUMN_MOVIE_RATING = "movie_rating";

        // column for storing the movie synopsis
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

        public static final String COLUMN_MOVIE_POSTER_PATH="poster_path";

        public static Uri buildMovieUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static Long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }


    /* Inner class that defines the table contents of the movies comments table */
    public static final class MovieCommentEntry implements BaseColumns {

        //movies comments table name
        public static final String TABLE_NAME = "comments";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_COMMENTS_PATH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_COMMENTS_PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_COMMENTS_PATH;


        //column for storing the movie comment
        public static final String COLUMN_MOVIE_COMMENT = "comment";
        //column with the foreign key into the movies table.
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //name of movie commenter
        public static final String COLUMN_AUTHOR_NAME = "author";

        public static Uri buildCommentUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static String getInsertedCommentId(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

}
