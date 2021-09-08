package com.acmestore.model.repository

import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User

interface UserRepository {

    fun get(user: User, token: String): StateLiveData<User>

    fun getInventory(user: User, token: String): StateLiveData<List<Product>>
}