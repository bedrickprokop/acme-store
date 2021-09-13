package com.acmestore.model.api

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("user/{userId}/{token}")
    suspend fun get(@Path("userId") userId: Int, @Path("token") token: String): Response<User>

    @GET("user/{userId}/inventory/{token}")
    suspend fun getInventory(
        @Path("userId") userId: Int,
        @Path("token") token: String
    ): Response<List<Product>>

}