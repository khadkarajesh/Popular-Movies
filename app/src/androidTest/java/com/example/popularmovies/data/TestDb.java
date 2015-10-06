package com.example.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.popularmovies.TestUtilities;
import com.example.popularmovies.data.MoviesContract.MovieCommentEntry;
import com.example.popularmovies.data.MoviesContract.MovieEntry;

import java.util.HashSet;

/**
 * Created by rajesh on 10/5/15.
 */
public class TestDb extends AndroidTestCase {
    private String TAG = TestDb.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext().deleteDatabase(MovieDbHelper.DATABASE_NAME);

    }

    public void testDataBaseCreation() {
        HashSet<String> tableNameSet = new HashSet<>();
        tableNameSet.add(MovieEntry.TABLE_NAME);
        tableNameSet.add(MovieCommentEntry.TABLE_NAME);

        getContext().deleteDatabase(MovieDbHelper.DATABASE_NAME);


        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        // have we created the tables we want?
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("dataabse is not created", c.moveToNext());


        // verify that the tables have been created
        do {
            tableNameSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameSet.isEmpty());


        // now, do our tables contain the correct columns?
        c = database.rawQuery("PRAGMA table_info(" + MovieEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        HashSet<String> movieColumnHashSet = new HashSet<>();

        movieColumnHashSet.add(MovieEntry._ID);
        movieColumnHashSet.add(MovieEntry.COLUMN_MOVIE_ID);
        movieColumnHashSet.add(MovieEntry.COLUMN_MOVIE_TITLE);
        movieColumnHashSet.add(MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
        movieColumnHashSet.add(MovieEntry.COLUMN_MOVIE_RATING);
        movieColumnHashSet.add(MovieEntry.COLUMN_MOVIE_OVERVIEW);


        int columnNameIndex = c.getColumnIndex("name");


        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        database.close();


    }

    public void testInsertMovieComment() {
        insertMovieComment();
    }

    public void testInsertDatabase() {

        SQLiteDatabase sqLiteDatabase;
        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());

        sqLiteDatabase = movieDbHelper.getWritableDatabase();


        ContentValues contentValue = TestUtilities.createMovieData();

        long recordId = sqLiteDatabase.insert(MovieEntry.TABLE_NAME, null, contentValue);

        assertTrue(recordId != -1);

        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);

        assertTrue(cursor.getCount() != -1);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    private void insertMovieComment() {
        SQLiteDatabase sqLiteDatabase;
        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());

        sqLiteDatabase = movieDbHelper.getWritableDatabase();

        ContentValues contentValue = TestUtilities.createMovieComments();

        long recordId = sqLiteDatabase.insert(MovieCommentEntry.TABLE_NAME, null, contentValue);

        assertTrue(recordId != -1);


        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);

        assertTrue(cursor.getCount() != -1);
    }

}
