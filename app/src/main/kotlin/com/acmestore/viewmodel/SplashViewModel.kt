package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserObservable(owner: User): StateLiveData<User> =
        userRepository.get(owner, Consts.TOKEN)
}