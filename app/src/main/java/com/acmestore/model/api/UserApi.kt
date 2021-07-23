package com.acmestore.model.api

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("user/{userId}?token={token}")
    fun get(@Path("userId") userId: Int, @Query("token") token: String): Call<User>

    @GET("user/{userId}/products?token={token}")
    fun getProducts(
        @Path("userId") userId: Int,
        @Query("token") token: String
    ): Call<List<Product>>

}