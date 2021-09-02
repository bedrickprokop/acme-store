package com.acmestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acmestore.Consts
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository

class SplashViewModel : ViewModel() {

    private val userRepository: UserRepository = UserRepository

    fun getUserObservable(): LiveData<User> {
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