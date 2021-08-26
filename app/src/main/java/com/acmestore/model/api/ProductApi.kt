package com.acmestore.model.api

import com.acmestore.model.entity.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {

    @GET("product/{token}")
    fun getAll(@Path("token") token: String): Call<List<Product>>

    @GET("product/{productId}?token={token}")
    fun get(@Path("productId") productId: Int, @Query("token") token: String): Call<Product>

    @POST("product/buy")
    fun buy(
        @Field("productId") productId: Int,
        @Field("userId") userId: Int,
        @Field("token") token: String
    ): Call<Product>

    @POST("product/sell")
    fun sell(
        @Field("productId") productId: Int,
        @Field("userId") userId: Int,
        @Field("token") token: String
    ): Call<Product>
}