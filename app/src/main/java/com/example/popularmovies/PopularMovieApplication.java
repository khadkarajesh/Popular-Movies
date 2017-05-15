package com.example.popularmovies;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import io.realm.Realm;
import timber.log.Timber;

public class PopularMovieApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        context=this;
        Realm.init(this);
    }

    public static Context get(){
        return context;
    }
}
