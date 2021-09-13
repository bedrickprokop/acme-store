package com.acmestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acmestore.Consts
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User
import com.acmestore.model.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val addToCartObservable: MutableLiveData<Product> = MutableLiveData<Product>()
    val buyObservable: MutableLiveData<List<Int>> = MutableLiveData<List<Int>>()
    val sellObservable: MutableLiveData<Product> = MutableLiveData<Product>()
    val errorObservable: MutableLiveData<String> = MutableLiveData<String>()

    fun addToCart(product: Product) = viewModelScope.launch {
        when (val result = productRepository.addToCart(product, Consts.TOKEN)) {
            is ApiResponse.Success -> addToCartObservable.postValue(result.data)
            is ApiResponse.Error -> errorObservable.postValue(result.errorMessage)
        }
    }

    fun buy(productIds: List<Int>, owner: User) = viewModelScope.launch {
        when (val result = productRepository.buy(productIds, owner, Consts.TOKEN)) {
            is ApiResponse.Success -> buyObservable.postValue(result.data)
            is ApiResponse.Error -> errorObservable.postValue(result.errorMessage)
        }
    }

    fun sell(product: Product) = viewModelScope.launch {
        when (val result = productRepository.sell(product, Consts.TOKEN)) {
            is ApiResponse.Success -> sellObservable.postValue(result.data)
            is ApiResponse.Error -> errorObservable.postValue(result.errorMessage)
        }
    }

}