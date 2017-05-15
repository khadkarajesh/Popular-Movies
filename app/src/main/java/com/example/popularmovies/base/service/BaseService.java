package com.example.popularmovies.base.service;


import com.example.popularmovies.data.manager.RealmManager;

public abstract class BaseService<T> implements IBaseService<T> {
    public RealmManager manager;

    public BaseService() {
        manager = new RealmManager();
    }
}
