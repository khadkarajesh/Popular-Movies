package com.example.popularmovies.data.service;


import com.example.popularmovies.base.service.BaseService;
import com.example.popularmovies.data.model.RealmMovieObject;
import com.example.popularmovies.rest.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MovieService extends BaseService<Movie> {

    @Override
    public void saveAll(final Movie movie) {
        manager.get().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieObject realmMovieObject = realm.createObject(RealmMovieObject.class);
                realmMovieObject.setId(movie.id);
                realmMovieObject.setOverview(movie.overview);
                realmMovieObject.setPopularity(movie.popularity);
                realmMovieObject.setPosterPath(movie.posterPath);
                realmMovieObject.setReleaseDate(movie.releaseDate);
                realmMovieObject.setTitle(movie.title);
                realmMovieObject.setVoteCount(movie.voteCount);
                realmMovieObject.setVoteAverage(movie.voteAverage);
            }
        });
        manager.close();
    }

    @Override
    public void saveAll(final List<Movie> t) {
        manager.get().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Movie movie : t) {
                    RealmMovieObject realmMovieObject = realm.createObject(RealmMovieObject.class);
                    realmMovieObject.setId(movie.id);
                    realmMovieObject.setOverview(movie.overview);
                    realmMovieObject.setPopularity(movie.popularity);
                    realmMovieObject.setPosterPath(movie.posterPath);
                    realmMovieObject.setReleaseDate(movie.releaseDate);
                    realmMovieObject.setTitle(movie.title);
                    realmMovieObject.setVoteCount(movie.voteCount);
                }
            }
        });
        manager.close();
    }

    @Override
    public Movie getById(int id) {
        return null;
    }

    @Override
    public void delete(Movie movie) {

    }

    @Override
    public Movie update(Movie movie) {
        return null;
    }

    @Override
    public List<Movie> getAll() {
        RealmResults<RealmMovieObject> results = manager.get().where(RealmMovieObject.class).findAll();
        List<Movie> movies = new ArrayList<>();
        for (RealmMovieObject movieObject : results) {
            Movie movie = new Movie();
            movie.id = movieObject.getId();
            movie.overview = movieObject.getOverview();
            movie.popularity = movieObject.getPopularity();
            movie.posterPath = movieObject.getPosterPath();
            movie.releaseDate = movieObject.getReleaseDate();
            movie.title = movieObject.getTitle();
            movie.voteCount = movieObject.getVoteCount();
            movie.voteAverage = movieObject.getVoteAverage();
            movies.add(movie);
        }
        return movies;
    }
}
