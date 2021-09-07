package com.acmestore.model.repository

import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.RequestProduct
import com.acmestore.model.entity.User
import com.acmestore.model.exception.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProductRepository {

    private var productApi: ProductApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)

    fun getAll(token: String): StateLiveData<List<Product>> {
        val data = StateLiveData<List<Product>>()

        productApi.getAll(token).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postSuccess(arrayListOf())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) = data.postError(t)
        })
        return data
    }

    fun addToCart(product: Product, token: String): StateLiveData<Product> {
        val data = StateLiveData<Product>()
        val requestAddCart = RequestProduct(token, product.id)

        productApi.addToCart(requestAddCart).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postError(
                    ApiException(
                        "Unexpected exception", response.code(), null, response.errorBody()
                    )
                )
            }

            override fun onFailure(call: Call<Product>, t: Throwable) = data.postError(t)
        })
        return data
    }

    fun buy(productIds: List<Int>, owner: User, token: String): StateLiveData<List<Int>> {
        val data = StateLiveData<List<Int>>()
        val requestBuy = RequestProduct(token, null, productIds)

        productApi.buy(requestBuy).enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postError(
                    ApiException(
                        "Unexpected exception", response.code(), null, response.errorBody()
                    )
                )
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) = data.postError(t)
        })
        return data
    }

    fun sell(product: Product, token: String): StateLiveData<Product> {
        val data = StateLiveData<Product>()
        val requestSell = RequestProduct(token, product.id)

        productApi.sell(requestSell).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful && response.body() != null) data.postSuccess(response.body()!!)
                else data.postError(
                    ApiException(
                        "Unexpected exception", response.code(), null, response.errorBody()
                    )
                )
            }

            override fun onFailure(call: Call<Product>, t: Throwable) = data.postError(t)
        })
        return data
    }
}