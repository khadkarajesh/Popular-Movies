package com.example.popularmovies.rest;

/**
 * Created by rajesh on 5/12/17.
 */

public class Envelope<T> {
    public T results;
    public int page;
    public int total_pages;
    public int total_results;
}
