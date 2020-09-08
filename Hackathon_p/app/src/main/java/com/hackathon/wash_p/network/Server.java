package com.hackathon.wash_p.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    private static Server instance;
    private ServerAPI api;

    private Server(){
        Retrofit server = new Retrofit.Builder()
                .baseUrl("http://10.80.161.219:8087")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = server.create(ServerAPI.class);
    }
    public static Server getInstance(){
        if(instance == null) instance = new Server();
        return instance;
    }
    public ServerAPI getApi(){
        return api;
    }
}
