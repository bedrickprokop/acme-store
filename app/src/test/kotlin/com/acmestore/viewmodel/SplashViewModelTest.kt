package com.acmestore.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acmestore.model.HttpApiGenerator
import com.acmestore.model.api.UserApi
import com.acmestore.model.entity.User
import com.acmestore.model.repository.impl.UserRepositoryImpl
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class SplashViewModelTest : TestCase() {

    @get: Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var splashViewModel: SplashViewModel

    @Before
    public override fun setUp() {
        val userApi = HttpApiGenerator<UserApi>().get(UserApi::class.java)
        val userRepository = UserRepositoryImpl(userApi)
        splashViewModel = SplashViewModel(userRepository)
    }

    @Test
    fun getUserTest() {
        val owner = User(
            1,
            "Bedrick Prokop",
            "bedrick@mymail.com",
            1000000.00,
            arrayListOf()
        )
        splashViewModel.getUser(owner)
        splashViewModel.userObservable.observe(getLifecycleOwner(), {
            assertNotNull(it)
        })
    }

    // Link: Mock lifecycle without Robolectric: https://stackoverflow.com/a/59613753
    // Link: https://gist.github.com/lvsecoto/a68b5feecf1f5e7eba418311009338cd
    private fun getLifecycleOwner(): LifecycleOwner {
        val owner = mock(LifecycleOwner::class.java)
        val lifecycle = LifecycleRegistry(owner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        `when`(owner.lifecycle).thenReturn(lifecycle)
        return owner
    }
}