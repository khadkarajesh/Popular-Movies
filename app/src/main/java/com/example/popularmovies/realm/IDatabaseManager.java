package com.example.popularmovies.realm;


public interface IDatabaseManager<T> {

    T initialize();

    void open();

    void close();

    void commit();

    T get();
}
