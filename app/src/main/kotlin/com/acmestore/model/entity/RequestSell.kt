package com.acmestore.model.entity

data class RequestSell(
    val productId: Int,
    val userId: Int,
    val token: String
)