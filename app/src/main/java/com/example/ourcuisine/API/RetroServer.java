package com.example.ourcuisine.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer
{
    private static final String serverAddress = "https://jendraja.000webhostapp.com/";
    private static Retrofit retrofit;
    public static Retrofit connectRetrofit()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(serverAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}