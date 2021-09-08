package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.repository.ProductRepository

class ShopViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun getAllProductsObservable(): StateLiveData<List<Product>> =
        productRepository.getAll(Consts.TOKEN)
}