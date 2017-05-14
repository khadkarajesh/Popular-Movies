package com.example.popularmovies.bus;

import com.example.popularmovies.rest.model.Movie;

/**
 * Events used in this application
 */
public class PopularMoviesEvent {
    public static final String EVENT_PREFERENCE = "EVENT.PREFERENCE";
    public static final String EVENT_MOVIES_POSTER_SELECTION = "EVENT.MOVIES_POSTER_SELECTION";
    public static final String EVENT_MOVIE_UNFAVOURITE = "EVENT.MOVIES_UNFAVOURITE";
    public static final String EVENT_SERVER_ERROR = "EVENT.SERVER_ERROR";
    public String mAction;

    public PopularMoviesEvent(String action) {
        this.mAction = action;
    }

    /**
     * event for listening the categories change from the settings.
     */
    public static class PreferenceChangeEvent extends PopularMoviesEvent {
        public PreferenceChangeEvent() {
            super(EVENT_PREFERENCE);
        }
    }

    public static class MoviePosterSelectionEvent extends PopularMoviesEvent {
        public Movie movie;

        public MoviePosterSelectionEvent(Movie movie) {
            super(EVENT_MOVIES_POSTER_SELECTION);
            this.movie = movie;
        }
    }

    public static class MovieUnFavourite extends PopularMoviesEvent {
        public MovieUnFavourite() {
            super(EVENT_MOVIE_UNFAVOURITE);
        }
    }

    public static class ServerErrorEvent extends PopularMoviesEvent {
        public String message;

        public ServerErrorEvent(String message) {
            super(EVENT_SERVER_ERROR);
            this.message = message;
        }
    }
}
