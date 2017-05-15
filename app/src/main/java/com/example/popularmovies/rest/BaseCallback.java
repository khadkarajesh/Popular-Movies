package com.example.popularmovies.rest;


import com.example.popularmovies.bus.EventBus;
import com.example.popularmovies.bus.PopularMoviesEvent;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            showErrorMessage(parseError(response.errorBody()).status_message);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        showErrorMessage(t.getLocalizedMessage());
    }

    public abstract void onSuccess(T t);

    public void showErrorMessage(String message) {
        EventBus.post(new PopularMoviesEvent.ServerErrorEvent(message));
    }

    public ServerError parseError(ResponseBody responseBody) {
        ServerError serverError = null;
        Converter<ResponseBody, ServerError> converter = RetrofitManager.retrofit.responseBodyConverter(ServerError.class, new Annotation[0]);
        try {
            serverError = converter.convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverError;
    }
}

