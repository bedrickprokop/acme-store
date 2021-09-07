package com.acmestore.model.entity

data class RequestProduct(
    val token: String,
    val productId: Int? = null,
    val productIds: List<Int>? = null,
)