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

// Link: Datasource x Repository: https://stackoverflow.com/a/66556192
object UserRepository {

    private var userApi: UserApi = HttpApiGenerator<UserApi>().get(UserApi::class.java)

    fun get(user: User, token: String): LiveData<User> {
        val data = MutableLiveData<User>()
        userApi.get(user.id, token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) data.postValue(response.body())
                // TODO Exception treatment
                else data.postValue(null)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // TODO Exception treatment
                data.postValue(null)
            }

        })
        return data
    }

    fun getProducts(user: User, token: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        userApi.getProducts(user.id, token).enqueue(object : Callback<List<Product>> {
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