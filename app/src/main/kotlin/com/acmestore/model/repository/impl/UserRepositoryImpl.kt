package com.acmestore.model.repository.impl

import android.util.Log
import com.acmestore.model.api.UserApi
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

private const val TAG = "UserRepositoryImpl"

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun get(user: User, token: String): ApiResponse<User?> {
        return try {
            val response = userApi.get(user.id, token)
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }

    override suspend fun getInventory(user: User, token: String): ApiResponse<List<Product>?> {
        return try {
            val response = userApi.getInventory(user.id, token)
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }
}