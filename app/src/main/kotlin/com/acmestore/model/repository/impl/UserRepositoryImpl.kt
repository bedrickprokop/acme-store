package com.acmestore.model.repository.impl

import com.acmestore.model.api.UserApi
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.model.exception.ApiException
import com.acmestore.model.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override fun get(user: User, token: String): StateLiveData<User> {
        val data = StateLiveData<User>()

        userApi.get(user.id, token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postError(ApiException("Unexpected exception", response.code(), null))
            }

            override fun onFailure(call: Call<User>, t: Throwable) = data.postError(t)
        })
        return data
    }

    override fun getInventory(user: User, token: String): StateLiveData<List<Product>> {
        val data = StateLiveData<List<Product>>()

        userApi.getInventory(user.id, token).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postSuccess(arrayListOf())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) = data.postError(t)
        })
        return data
    }
}