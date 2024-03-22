package com.example.asmand03.api;

import com.example.asmand03.model.PhoneModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServer {
    @GET("/")
    Call<List<PhoneModel>> getPhones();

    @POST("/")
    Call<List<PhoneModel>> addPhone(@Body PhoneModel phone);

    @DELETE("/{phoneId}")
    Call<List<PhoneModel>> deletePhone(@Path("phoneId") String phoneId);

    @PUT("/{phoneId}")
    Call<PhoneModel> updatePhone(@Path("phoneId") String phoneId,@Body PhoneModel phone);

}
