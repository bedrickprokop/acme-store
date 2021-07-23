package com.acmestore.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.UserApi
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    private var userApi: UserApi = HttpApiGenerator<UserApi>().get(UserApi::class.java)

    fun get(user: User, token: String): LiveData<User> {
        val data = MutableLiveData<User>()
        val call = userApi.get(user.id, token)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // TODO Not yet implemented
                data.postValue(null)
            }

        })
        return data
    }

    fun getProducts(user: User, token: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        val call = userApi.getProducts(user.id, token)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // TODO Not yet implemented
            }
        })
        return data
    }
}