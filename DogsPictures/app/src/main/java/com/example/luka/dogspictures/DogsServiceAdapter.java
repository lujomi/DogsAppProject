package com.example.luka.dogspictures;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsServiceAdapter {

    public static DogsServiceApi provideApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dog.ceo/api/breeds/")
                .client(new OkHttpClient())
                .build();

        return retrofit.create(DogsServiceApi.class);
    }

}
