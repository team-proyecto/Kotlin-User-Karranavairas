package com.example.covid.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {
    private static Retrofit retrofit = null;

    //private static final String REST ="http://localhost:8090/api/";
    private static final String REST ="https://corona-spring.herokuapp.com/api/";



    public static Retrofit getConnection() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(REST).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
