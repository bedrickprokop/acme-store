package com.acmestore.model.api

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.RequestProduct
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {

    @GET("product/{token}")
    fun getAll(@Path("token") token: String): Call<List<Product>>

    @GET("product/{productId}?token={token}")
    fun get(@Path("productId") productId: Int, @Query("token") token: String): Call<Product>

    @POST("product/addcart")
    fun addToCart(@Body requestAddCart: RequestProduct): Call<Product>

    @POST("product/buy")
    fun buy(@Body requestBuy: RequestProduct): Call<List<Int>>

    @POST("product/sell")
    fun sell(@Body requestSell: RequestProduct): Call<Product>
}