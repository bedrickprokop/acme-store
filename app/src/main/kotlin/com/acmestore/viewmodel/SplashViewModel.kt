package com.acmestore.viewmodel

import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.data.StateLiveData
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

class SplashViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepository

    fun getUserObservable(): StateLiveData<User> {
        // TODO check if user exists in localstorage else find it in api service
        return userRepository.get(
            User(
                1,
                "Bedrick Prokop",
                "bedrick@mymail.com",
                1000000.00,
                arrayListOf()
            ), Consts.TOKEN
        )
    }

}