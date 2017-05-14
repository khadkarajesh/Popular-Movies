package com.example.popularmovies.rest.converters;

import com.example.popularmovies.rest.Envelope;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class ResponseEnvelopeConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, final Retrofit retrofit) {
        Type envelopeType = TypeToken.getParameterized(Envelope.class, type).getType();
        final Converter<ResponseBody, Envelope<?>> delegate = retrofit.nextResponseBodyConverter(this, envelopeType, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody value) throws IOException {
                Envelope<?> envelope = delegate.convert(value);
                return envelope.results;
            }
        };
    }
}
