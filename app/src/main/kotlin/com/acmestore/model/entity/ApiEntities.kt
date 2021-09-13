package com.acmestore.model.entity

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}

data class ProductRequest(
    val token: String,
    val productId: Int? = null,
    val productIds: List<Int>? = null,
)