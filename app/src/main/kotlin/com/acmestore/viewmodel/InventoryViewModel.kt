package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

class InventoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getInventoryObservable(owner: User): StateLiveData<List<Product>> =
        userRepository.getInventory(owner, Consts.TOKEN)
}