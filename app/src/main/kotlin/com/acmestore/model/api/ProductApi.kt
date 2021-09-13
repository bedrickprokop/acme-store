package com.acmestore.model.api

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ProductRequest
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    @GET("product/{token}")
    suspend fun getAll(@Path("token") token: String): Response<List<Product>>

    @GET("product/{productId}?token={token}")
    suspend fun get(
        @Path("productId") productId: Int,
        @Query("token") token: String
    ): Response<Product>

    @POST("product/addcart")
    suspend fun addToCart(@Body requestAddCart: ProductRequest): Response<Product>

    @POST("product/buy")
    suspend fun buy(@Body requestBuy: ProductRequest): Response<List<Int>>

    @POST("product/sell")
    suspend fun sell(@Body requestSell: ProductRequest): Response<Product>
}