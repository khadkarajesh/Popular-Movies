package com.example.popularmovies.rest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
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

    }
}

