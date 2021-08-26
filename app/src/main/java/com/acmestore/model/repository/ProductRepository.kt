package com.acmestore.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.entity.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    private var productApi: ProductApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)

    fun getAll(token: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        val call = productApi.getAll(token)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) data.postValue(response.body())
                // TODO Exception treatment
                else data.postValue(null)
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // TODO Exception treatment
                data.postValue(null)
            }
        })
        return data
    }

}