package com.acmestore.model.entity

data class RequestBuy(
    val productIds: List<Int>,
    val userId: Int,
    val token: String
)