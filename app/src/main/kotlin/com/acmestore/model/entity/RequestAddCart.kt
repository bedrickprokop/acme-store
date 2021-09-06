package com.acmestore.model.entity

data class RequestAddCart(
    val productId: Int,
    val userId: Int,
    val token: String
)