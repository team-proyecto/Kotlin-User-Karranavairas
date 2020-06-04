package com.example.covid.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {
    private static Retrofit retrofit = null;

    private static final String REST ="http://localhost:8080/api/";


    public static Retrofit getConnection() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(REST).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
