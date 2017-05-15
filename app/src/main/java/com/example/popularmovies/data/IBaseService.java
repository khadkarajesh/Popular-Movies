package com.example.popularmovies.data;


import java.util.List;

public interface IBaseService<T> {
    void saveAll(T t);

    void saveAll(List<T> t);

    T getById(int id);

    void delete(T t);

    T update(T t);

    List<T> getAll();
}
