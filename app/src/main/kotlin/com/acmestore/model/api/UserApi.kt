package com.acmestore.model.api

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("user/{userId}/{token}")
    fun get(@Path("userId") userId: Int, @Path("token") token: String): Call<User>

    @GET("user/{userId}/inventory/{token}")
    fun getInventory(
        @Path("userId") userId: Int,
        @Path("token") token: String
    ): Call<List<Product>>

}