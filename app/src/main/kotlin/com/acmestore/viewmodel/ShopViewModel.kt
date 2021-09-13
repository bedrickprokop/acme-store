package com.acmestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acmestore.Consts
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.repository.impl.ProductRepositoryImpl
import kotlinx.coroutines.launch

class ShopViewModel(private val productRepository: ProductRepositoryImpl) : ViewModel() {

    val allProductsObservable: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    val errorObservable: MutableLiveData<String> = MutableLiveData<String>()

    fun getAllProducts() {
        viewModelScope.launch {
            when (val result = productRepository.getAll(Consts.TOKEN)) {
                is ApiResponse.Success -> allProductsObservable.postValue(result.data)
                is ApiResponse.Error -> errorObservable.postValue(result.errorMessage)
            }
        }
    }
}