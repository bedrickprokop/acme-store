package com.acmestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acmestore.Consts
import com.acmestore.model.entity.ApiResponse
import com.acmestore.model.entity.User
import com.acmestore.model.repository.UserRepository
import kotlinx.coroutines.launch

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userObservable: MutableLiveData<User> = MutableLiveData<User>()
    val errorObservable: MutableLiveData<String> = MutableLiveData<String>()

    fun getUser(owner: User) {
        viewModelScope.launch {
            when (val result = userRepository.get(owner, Consts.TOKEN)) {
                is ApiResponse.Success -> userObservable.postValue(result.data)
                is ApiResponse.Error -> errorObservable.postValue(result.errorMessage)
            }
        }
    }
}