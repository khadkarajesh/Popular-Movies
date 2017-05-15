package com.example.popularmovies.base.data;


public interface IDatabaseManager<T> {

    T initialize();

    void open();

    void close();

    void commit();

    T get();
}
