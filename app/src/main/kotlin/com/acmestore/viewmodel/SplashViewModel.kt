package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

class SplashViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepository

    fun getUserObservable(owner: User): StateLiveData<User> =
        userRepository.get(owner, Consts.TOKEN)
}