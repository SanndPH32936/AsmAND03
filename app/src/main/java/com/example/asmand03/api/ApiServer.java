package com.example.asmand03.api;

import com.example.asmand03.model.PhoneModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiServer {
    public static final  String IPv4 = "192.168.1.4";
    @GET("/")
    Call<List<PhoneModel>> getPhones();

    @POST("/")
    Call<List<PhoneModel>> addPhone(@Body PhoneModel model);

    @DELETE("/{phoneId}")
    Call<List<PhoneModel>> deletePhone(@Path("phoneId") String phoneId);

    @PUT("/{phoneId}")
    Call<PhoneModel> updatePhone(@Path("phoneId") String phoneId,@Body PhoneModel phone);

}
