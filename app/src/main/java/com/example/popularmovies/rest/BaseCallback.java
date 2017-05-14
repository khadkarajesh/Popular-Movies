package com.example.popularmovies.rest;


import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
            showErrorMessage("This is error");
        } else {
            showErrorMessage(response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(call, t.getLocalizedMessage());
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Call<T> call, String message);

    public void showErrorMessage(String message) {
        EventBus.post(new PopularMoviesEvent.ServerErrorEvent(message));
    }
}

