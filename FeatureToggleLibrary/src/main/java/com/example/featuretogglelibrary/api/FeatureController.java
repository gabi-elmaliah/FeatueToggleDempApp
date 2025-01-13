package com.example.featuretogglelibrary.api;

import com.example.featuretogglelibrary.interfaces.FeatureApi;
import com.example.featuretogglelibrary.model.FeatureToggleItem;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FeatureController {

    private static final String BASE_URL = "http://127.0.0.1:5000/"; // Replace with your server's base URL

    private FeatureApi getAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setLenient()
                                        .create()
                        )
                )
                .build();

        return retrofit.create(FeatureApi.class);
    }











}
