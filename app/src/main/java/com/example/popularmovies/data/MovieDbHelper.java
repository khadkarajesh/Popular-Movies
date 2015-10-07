package com.example.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.popularmovies.data.MoviesContract.MovieEntry;
import com.example.popularmovies.data.MoviesContract.MovieCommentEntry;

/**
 * Created by rajesh on 10/4/15.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE "
                        + MovieEntry.TABLE_NAME
                        + " ("
                        + MovieEntry._ID + " INTEGER PRIMARY KEY,"
                        + MovieEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL,"
                        + MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"
                        + MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL,"
                        + MovieEntry.COLUMN_MOVIE_RATING + " REAL NOT NULL,"
                        + MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL"
                        + ");";

        final String SQL_CREATE_MOVIE_COMMENTS_TABLE =
                "CREATE TABLE "
                        + MovieCommentEntry.TABLE_NAME
                        + " ("
                        + MovieCommentEntry._ID + " INTEGER PRIMARY KEY,"
                        + MovieCommentEntry.COLUMN_MOVIE_COMMENT + " TEXT NOT NULL,"
                        + MovieCommentEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL,"
                        + " FOREIGN KEY (" + MovieCommentEntry.COLUMN_MOVIE_ID + ")"
                        + " REFERENCES " + MovieEntry.TABLE_NAME + "(" + MovieEntry.COLUMN_MOVIE_ID + ")"
                        + " );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieCommentEntry.TABLE_NAME);
        onCreate(db);
    }
}
