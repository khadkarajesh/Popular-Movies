package com.example.popularmovies.bus;

import com.example.popularmovies.rest.model.Movie;

/**
 * Events used in this application
 */
public class PopularMoviesEvent {

    /**
     * event for listening the categories change from the settings.
     */
    public static class PreferenceChangeEvent {
        public PreferenceChangeEvent() {
        }
    }

    public static class MoviePosterSelectionEvent {
        public Movie movie;

        public MoviePosterSelectionEvent(Movie movie) {
            this.movie = movie;
        }
    }

    public static class MovieUnFavourite {

        public MovieUnFavourite() {

        }
    }
}
