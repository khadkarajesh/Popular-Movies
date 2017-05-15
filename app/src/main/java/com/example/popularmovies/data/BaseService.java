package com.example.popularmovies.data;


import com.example.popularmovies.realm.RealmManager;

public abstract class BaseService<T> implements IBaseService<T> {
    public RealmManager manager;

    public BaseService() {
        manager = new RealmManager();
    }
}
