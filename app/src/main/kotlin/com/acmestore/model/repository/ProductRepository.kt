package com.acmestore.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    private var productApi: ProductApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)

    fun getAll(token: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()

        productApi.getAll(token).enqueue(object : Callback<List<Product>> {
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

    fun addToCart(product: Product, token: String): LiveData<Product> {
        val data = MutableLiveData<Product>()
        product.owner?.let {
            val requestAddCart = RequestAddCart(product.id, product.owner!!.id, token)
            productApi.addToCart(requestAddCart).enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful) data.postValue(response.body())
                    // TODO Exception treatment
                    else data.postValue(null)
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    data.postValue(null)
                }
            })
        }
        return data
    }

    fun buy(productIds: List<Int>, owner: User, token: String): LiveData<List<Int>> {
        val data = MutableLiveData<List<Int>>()
        val requestBuy = RequestBuy(productIds, owner.id, token)

        productApi.buy(requestBuy).enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) data.postValue(response.body())
                // TODO Exception treatment
                else data.postValue(null)
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                data.postValue(null)
            }
        })
        return data
    }

    fun sell(product: Product, token: String): LiveData<Product> {
        val data = MutableLiveData<Product>()
        product.owner?.let {
            val requestSell = RequestSell(product.id, product.owner!!.id, token)

            productApi.sell(requestSell).enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful) data.postValue(response.body())
                    // TODO Exception treatment
                    else data.postValue(null)
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    data.postValue(null)
                }
            })
        }
        return data
    }
}