package com.example.ourcuisine.API;

import com.example.ourcuisine.Model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData
{
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieve();
    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreate(
            @Field("name") String name,
            @Field("origin") String origin,
            @Field("description") String description
    );
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdate(
            @Field("id") String id,
            @Field("name") String name,
            @Field("origin") String origin,
            @Field("description") String description
    );
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDelete(@Field("id") String id);
}