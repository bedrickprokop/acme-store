package com.acmestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acmestore.Consts
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository
import kotlinx.coroutines.launch

class InventoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    val inventoryObservable: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    val errorObservable: MutableLiveData<String> = MutableLiveData<String>()

    fun getInventory(owner: User) {
        viewModelScope.launch {
            when (val result = userRepository.getInventory(owner, Consts.TOKEN)) {
                is ApiResponse.Success -> inventoryObservable.value = result.data
                is ApiResponse.Error -> errorObservable.value = result.errorMessage
            }
        }
    }
}