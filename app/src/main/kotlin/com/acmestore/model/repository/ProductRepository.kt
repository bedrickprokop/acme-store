package com.acmestore.model.repository

import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User

interface ProductRepository {

    fun getAll(token: String): StateLiveData<List<Product>>

    fun addToCart(product: Product, token: String): StateLiveData<Product>

    fun buy(productIds: List<Int>, owner: User, token: String): StateLiveData<List<Int>>

    fun sell(product: Product, token: String): StateLiveData<Product>
}