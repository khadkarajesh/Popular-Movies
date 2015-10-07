/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.popularmovies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;


public class TestUriMatcher extends AndroidTestCase {



    private static final int MOVIE_ID=13343;
    private static final Uri TEST_MOVIE_DIR = MoviesContract.MovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_WITH_SINGLE_ID_ITEM = MoviesContract.MovieEntry.buildMovieUri(MOVIE_ID);
    private static final Uri TEST_COMMENTS_URI = MoviesContract.MovieCommentEntry.buildCommentUri(MOVIE_ID);


    public void testUriMatcher() {

        UriMatcher uriMatcher = MovieDbProvider.buildUriMatcher();
        assertEquals("Error : THe movie Uri is not Matched", uriMatcher.match(TEST_MOVIE_DIR), MovieDbProvider.MOVIES);
        assertEquals("Error: The MOvie uri with id doesnt matched", uriMatcher.match(TEST_MOVIE_WITH_SINGLE_ID_ITEM), MovieDbProvider.MOVIES_BY_MOVIE_ID);
        assertEquals("Erro :The comment of of Movie Id",uriMatcher.match(TEST_COMMENTS_URI),MovieDbProvider.MOVIES_COMMENT_BY_MOVIE_ID);
    }
}
