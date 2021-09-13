package com.acmestore.model.repository

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User

interface ProductRepository {

    suspend fun getAll(token: String): ApiResponse<List<Product>?>

    suspend fun addToCart(product: Product, token: String): ApiResponse<Product?>

    suspend fun buy(productIds: List<Int>, owner: User, token: String): ApiResponse<List<Int>?>

    suspend fun sell(product: Product, token: String): ApiResponse<Product?>
}