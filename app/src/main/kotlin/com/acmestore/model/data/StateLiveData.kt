package com.acmestore.model.data

import androidx.lifecycle.MutableLiveData

class StateLiveData<T> : MutableLiveData<StateData<T>?>() {

    fun postSuccess(data: T) = postValue(StateData<T>().withSuccess(data))

    fun postError(throwable: Throwable?) = postValue(StateData<T>().withError(throwable))

    fun postLoading() = postValue(StateData<T>().withLoading())

    fun postLoadingComplete() = postValue(StateData<T>().withLoadingComplete())

}