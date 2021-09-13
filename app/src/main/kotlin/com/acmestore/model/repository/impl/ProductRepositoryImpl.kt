package com.acmestore.model.repository.impl

import android.util.Log
import com.acmestore.model.api.ProductApi
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ProductRequest
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User
import com.acmestore.model.repository.ProductRepository

private const val TAG = "ProductRepositoryImpl"

class ProductRepositoryImpl(private val productApi: ProductApi) : ProductRepository {

    override suspend fun getAll(token: String): ApiResponse<List<Product>?> {
        return try {
            val response = productApi.getAll(token)
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }

    override suspend fun addToCart(product: Product, token: String): ApiResponse<Product?> {
        return try {
            val response = productApi.addToCart(ProductRequest(token, product.id))
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }

    override suspend fun buy(
        productIds: List<Int>,
        owner: User,
        token: String
    ): ApiResponse<List<Int>?> {
        return try {
            val response = productApi.buy(ProductRequest(token, null, productIds))
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }

    override suspend fun sell(product: Product, token: String): ApiResponse<Product?> {
        return try {
            val response = productApi.sell(ProductRequest(token, product.id))
            if (response.isSuccessful) ApiResponse.Success(response.body())
            else ApiResponse.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            ApiResponse.Error("An unexpected error has occurred")
        }
    }
}