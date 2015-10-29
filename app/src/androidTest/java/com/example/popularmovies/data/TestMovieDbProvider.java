package com.example.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.popularmovies.TestUtilities;
import com.example.popularmovies.data.MoviesContract.MovieEntry;

/**
 * Created by rajesh on 10/7/15.
 */
public class TestMovieDbProvider extends AndroidTestCase {
    private static final String TAG = TestMovieDbProvider.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetType() {
        int movieId = 1232;
        Context context = getContext();

        assertEquals("The movie Uri type does'nt match", context.getContentResolver().getType(MoviesContract.MovieEntry.CONTENT_URI), MoviesContract.MovieEntry.CONTENT_TYPE);
        assertEquals("",context.getContentResolver().getType(MoviesContract.MovieEntry.buildMovieUri(movieId)),MoviesContract.MovieEntry.CONTENT_ITEM_TYPE);
        assertEquals("", context.getContentResolver().getType(MoviesContract.MovieCommentEntry.buildCommentUri(movieId)), MoviesContract.MovieCommentEntry.CONTENT_TYPE);

    }



    public void testInsertMovieAndComment()
    {

        int movieId=124;
        Uri uri=getContext().getContentResolver().insert(MovieEntry.CONTENT_URI, TestUtilities.createMovieData(movieId));
        Log.e(TAG, "movieUri" + uri);
        //int insertedId=Integer.parseInt(MovieEntry.getInsertedMovieId(uri));
        //assertTrue(insertedId>0);

        Uri uri1=getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI,TestUtilities.createMovieComments(movieId));
        int insertedCommentId=Integer.parseInt(MoviesContract.MovieCommentEntry.getInsertedCommentId(uri1));
        Log.e(TAG, "commentUri" + uri1);
    }

    public void testGetMovieAndCommentData()
    {

        getContext().deleteDatabase(MovieDbHelper.DATABASE_NAME);

        int movieId=1233;
        Uri uri=getContext().getContentResolver().insert(MovieEntry.CONTENT_URI,TestUtilities.createMovieData(movieId));
        getContext().getContentResolver().insert(MovieEntry.CONTENT_URI,TestUtilities.createMovieData(2342));
        getContext().getContentResolver().insert(MovieEntry.CONTENT_URI,TestUtilities.createMovieData(235));
        getContext().getContentResolver().insert(MovieEntry.CONTENT_URI,TestUtilities.createMovieData(239));
        getContext().getContentResolver().insert(MovieEntry.CONTENT_URI, TestUtilities.createMovieData(200));

        Log.e(TAG, "movieUri" + uri);
       // int insertedId=Integer.parseInt(MovieEntry.getInsertedMovieId(uri));
        //assertTrue(insertedId>0);

        Uri uri1=getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, TestUtilities.createMovieComments(movieId));
        int insertedCommentId=Integer.parseInt(MoviesContract.MovieCommentEntry.getInsertedCommentId(uri1));
        getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, TestUtilities.createMovieComments(23424));
        getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, TestUtilities.createMovieComments(235));
        getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, TestUtilities.createMovieComments(239));
        getContext().getContentResolver().insert(MoviesContract.MovieCommentEntry.CONTENT_URI, TestUtilities.createMovieComments(200));

        Log.e(TAG,"commentUri"+uri1);

        Cursor cursor=getContext().getContentResolver().query(MovieEntry.CONTENT_URI,null,null,null,null);
        int movieFromDbId = 0;
        while (cursor.moveToNext())
        {
            movieFromDbId=cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID));
            break;
        }

       assertTrue(movieFromDbId==1233);

    }

    public void testUpdateMethod()
    {

        long id=getContext().getContentResolver().update(MovieEntry.CONTENT_URI,TestUtilities.createUpdatedMovieData(1233),MovieDbProvider.updateMovieById,new String[]{"1233"});
        Log.e(TAG,"assertId"+id);
        assertTrue(id==1);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
