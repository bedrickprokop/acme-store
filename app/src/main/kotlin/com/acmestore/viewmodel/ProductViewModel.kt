package com.acmestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.model.repository.ProductRepository

class ProductViewModel : ViewModel() {

    private val productRepository: ProductRepository = ProductRepository

    fun addToCartObservable(product: Product): LiveData<Product> =
        productRepository.addToCart(product, Consts.TOKEN)

    fun buyObservable(productIds: List<Int>, owner: User): LiveData<List<Int>> =
        productRepository.buy(productIds, owner, Consts.TOKEN)

    fun sellObservable(product: Product): LiveData<Product> =
        productRepository.sell(product, Consts.TOKEN)
}