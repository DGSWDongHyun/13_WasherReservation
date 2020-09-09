package com.hackathon.wash_p.network;

import com.hackathon.wash_p.data.request.apply.Apply_wash;
import com.hackathon.wash_p.data.request.delete.Delete_wash;
import com.hackathon.wash_p.data.response.list.List_wash;
import com.hackathon.wash_p.data.response.result.Data_result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("/washerCtrl/washerRegister")
    Call<Data_result> putData(@Body Apply_wash wash_Object);

    @GET("/washerCtrl/getWashers")
    Call<List<List_wash>> getData();

    @POST
    Call<Data_result> deleteData(@Body Delete_wash delete_wash);
}
