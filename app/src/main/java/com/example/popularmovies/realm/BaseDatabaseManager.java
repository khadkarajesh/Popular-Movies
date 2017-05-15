package com.example.popularmovies.realm;


public abstract class BaseDatabaseManager<T> implements IDatabaseManager<T> {
    public T t;

    public BaseDatabaseManager() {
        t = initialize();
    }
}
