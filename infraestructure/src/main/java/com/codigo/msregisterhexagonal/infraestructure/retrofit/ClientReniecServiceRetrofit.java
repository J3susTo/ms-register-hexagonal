package com.codigo.msregisterhexagonal.infraestructure.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientReniecServiceRetrofit {
    //SINGLETON
    private static Retrofit retrofit = null;
    public static Retrofit getNewRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.apis.net.pe")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
