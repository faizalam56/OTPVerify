package com.athira.otpverify.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIInterface {
    @GET("index.php/")
    Call<JsonObject> getOTP(@QueryMap Map<String,String> option);

    @GET("index.php/")
    Call<JsonObject> verifyOTP(@QueryMap Map<String,String> option);

    @GET("albums")
    Call<JsonArray> getAlbum();

    @GET("photos")
    Call<JsonArray> getPhotos(@QueryMap Map<String,String> option);
}
