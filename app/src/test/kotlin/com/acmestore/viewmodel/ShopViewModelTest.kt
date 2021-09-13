package com.acmestore.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.ProductApi
import com.acmestore.model.repository.impl.ProductRepositoryImpl
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class ShopViewModelTest : TestCase() {

    @get: Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var shopViewModel: ShopViewModel

    @Before
    public override fun setUp() {
        val productApi = HttpApiGenerator<ProductApi>().get(ProductApi::class.java)
        val productRepository = ProductRepositoryImpl(productApi)
        shopViewModel = ShopViewModel(productRepository)
    }

    @Test
    fun getAllProducts() {
        shopViewModel.getAllProducts()
        shopViewModel.allProductsObservable.observe(getLifecycleOwner(), {
            assertNotNull(it)
        })
    }

    // Link: Mock lifecycle without Robolectric: https://stackoverflow.com/a/59613753
    // Link: https://gist.github.com/lvsecoto/a68b5feecf1f5e7eba418311009338cd
    private fun getLifecycleOwner(): LifecycleOwner {
        val owner = Mockito.mock(LifecycleOwner::class.java)
        val lifecycle = LifecycleRegistry(owner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        Mockito.`when`(owner.lifecycle).thenReturn(lifecycle)
        return owner
    }
}