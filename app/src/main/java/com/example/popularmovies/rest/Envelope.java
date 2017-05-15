package com.example.popularmovies.rest;



public class Envelope<T> {
    public T results;
    public int page;
    public int total_pages;
    public int total_results;
}
