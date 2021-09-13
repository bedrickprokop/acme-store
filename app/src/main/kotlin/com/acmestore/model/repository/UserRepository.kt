package com.acmestore.model.repository

import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User

interface UserRepository {

    suspend fun get(user: User, token: String): ApiResponse<User?>

    suspend fun getInventory(user: User, token: String): ApiResponse<List<Product>?>
}