package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import com.acmestore.model.repository.ProductRepository

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun addToCartObservable(product: Product): StateLiveData<Product> =
        productRepository.addToCart(product, Consts.TOKEN)

    fun buyObservable(productIds: List<Int>, owner: User): StateLiveData<List<Int>> =
        productRepository.buy(productIds, owner, Consts.TOKEN)

    fun sellObservable(product: Product): StateLiveData<Product> =
        productRepository.sell(product, Consts.TOKEN)
}