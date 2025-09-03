package com.codigo.msregisterhexagonal.infraestructure.retrofit;

import com.codigo.msregisterhexagonal.infraestructure.response.ResponseReniec;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClienteReniecRetrofit {

    @GET("/v2/reniec/dni")
    Call<ResponseReniec> getInfoReniec(@Header("Authorization") String token,
                                       @Query("numero") String numero);
}
