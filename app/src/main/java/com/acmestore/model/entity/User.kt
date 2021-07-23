package com.acmestore.model.entity

class User(
    val id: Int,
    val name: String,
    val email: String,
    val money: Double,
    val products: List<Product>
)