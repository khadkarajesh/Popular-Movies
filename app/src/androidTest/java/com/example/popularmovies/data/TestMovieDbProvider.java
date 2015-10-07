package com.example.popularmovies.data;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * Created by rajesh on 10/7/15.
 */
public class TestMovieDbProvider extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetType() {
        int movieId = 1232;
        Context context = getContext();

        assertEquals("The movie Uri type does'nt match", context.getContentResolver().getType(MoviesContract.MovieEntry.CONTENT_URI), MoviesContract.MovieEntry.CONTENT_TYPE);
        assertEquals("",context.getContentResolver().getType(MoviesContract.MovieEntry.buildMovieUri(movieId)),MoviesContract.MovieEntry.CONTENT_ITEM_TYPE);
        assertEquals("",context.getContentResolver().getType(MoviesContract.MovieCommentEntry.buildCommentUri(movieId)),MoviesContract.MovieCommentEntry.CONTENT_TYPE);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
