package com.acmestore.model.repository.impl

import com.acmestore.model.entity.ApiResponse
import retrofit2.HttpException
import retrofit2.Response

open class BaseRepository {

    // TODO studying if it is a good practice
    protected suspend fun <T> getResult(call: suspend () -> T): ApiResponse<*> {
        return try {
            val response = call.invoke() as Response<*>
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            if (e is HttpException) ApiResponse.Error(e.message())
            else ApiResponse.Error("An unexpected error has occurred")
        }
    }

}