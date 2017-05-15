package com.example.popularmovies.base.data;


public abstract class BaseDatabaseManager<T> implements IDatabaseManager<T> {
    public T t;

    public BaseDatabaseManager() {
        t = initialize();
    }
}
