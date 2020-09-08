package com.hackathon.wash_p.network;

import com.hackathon.wash_p.data.request.Apply_wash;
import com.hackathon.wash_p.data.response.List_wash;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("/")
    Call<List<List_wash>> putData(@Body Apply_wash wash_Object);

    @GET("/washerCtrl/getWashers")
    Call<List<List_wash>> getData();
}
