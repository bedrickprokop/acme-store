package com.acmestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.entity.Product
import com.acmestore.model.repository.ProductRepository

class ShopViewModel : ViewModel() {
    private val productRepository: ProductRepository = ProductRepository

    fun getAllProductsObservable(): LiveData<List<Product>> = productRepository.getAll(Consts.TOKEN)

}