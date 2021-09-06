package com.acmestore.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acmestore.model.entity.Product
import com.acmestore.model.entity.User
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class ProductViewModelTest : TestCase() {

    @get: Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var productViewModel: ProductViewModel
    private lateinit var product: Product
    private lateinit var owner: User

    @Before
    public override fun setUp() {
        productViewModel = ProductViewModel()
        owner = User(1, null, null, 10000.32, null)
        product = Product(1, "Boomerang", 33.45, null, null, owner)
    }

    @Test
    public fun addToCart() {
        productViewModel.addToCartObservable(product).observe(getLifecycleOwner(), {
            assertNotNull(it)
            // Returns id = 4 because the mock in MockClient
            // TODO study how to treat mock and prod in tests
            assertEquals(4, it.id)
        })
    }

    @Test
    public fun buy() {
        val productIds = arrayListOf(1, 3, 4)
        productViewModel.buyObservable(productIds, owner).observe(getLifecycleOwner(), {
            assertNotNull(it)
            // Returns the mock response
            // TODO study how to treat mock and prod in tests
            assertEquals(3, it.size)
            assertEquals(2, it[0])
            assertEquals(4, it[1])
            assertEquals(5, it[2])
        })
    }

    @Test
    public fun sell() {
        productViewModel.sellObservable(product).observe(getLifecycleOwner(), {
            assertNotNull(it)
            // Returns id = 1 because the mock in MockClient
            // TODO study how to treat mock and prod in tests
            assertEquals(1, it.id)
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