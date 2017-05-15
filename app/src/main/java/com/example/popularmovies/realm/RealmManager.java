package com.example.popularmovies.realm;


import io.realm.Realm;

public class RealmManager extends BaseDatabaseManager<Realm> {
    @Override
    public Realm initialize() {
        t = Realm.getDefaultInstance();
        return t;
    }

    @Override
    public void open() {
        t.beginTransaction();
    }

    @Override
    public void close() {
        t.close();
    }

    @Override
    public void commit() {
        t.commitTransaction();
    }

    @Override
    public Realm get() {
        return t;
    }
}
